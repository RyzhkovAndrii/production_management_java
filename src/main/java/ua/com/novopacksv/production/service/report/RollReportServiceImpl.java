package ua.com.novopacksv.production.service.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.model.reportModel.RollReport;
import ua.com.novopacksv.production.model.rollModel.OperationType;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollTypeRepository;
import ua.com.novopacksv.production.service.plan.RollPlanOperationService;
import ua.com.novopacksv.production.service.roll.RollOperationService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollReportServiceImpl implements RollReportService {

    private final RollTypeRepository rollTypeRepository;

    private final RollOperationService rollOperationService;

    private final RollPlanOperationService rollPlanOperationService;

    @Override
    public List<RollReport> getAll(LocalDate from, LocalDate to) {
        return rollTypeRepository
                .findAll()
                .stream()
                .map(type -> getReport(type, from, to))
                .filter(this::isReportNotEmpty)
                .collect(Collectors.toList());
    }

    private RollReport getReport(RollType type, LocalDate from, LocalDate to) {
        Integer actual = getActualAmount(type, from, to);
        Integer plan = getPlanAmount(type, from, to);
        RollReport report = new RollReport();
        report.setRollType(type);
        report.setActualAmount(actual);
        report.setPlanAmount(plan);
        return report;
    }

    private Integer getActualAmount(RollType type, LocalDate from, LocalDate to) {
        return rollOperationService
                .findAllByRollTypeIdAndManufacturedPeriod(type.getId(), from, to)
                .stream()
                .filter(operation -> operation.getOperationType().equals(OperationType.MANUFACTURE))
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

    private Integer getPlanAmount(RollType type, LocalDate from, LocalDate to) {
        return rollPlanOperationService
                .findAll(type.getId(), from, to)
                .stream()
                .mapToInt(RollPlanOperation::getRollQuantity)
                .sum();
    }

    private boolean isReportNotEmpty(RollReport report) {
        return report.getActualAmount() != 0 || report.getPlanAmount() != 0;
    }

}
