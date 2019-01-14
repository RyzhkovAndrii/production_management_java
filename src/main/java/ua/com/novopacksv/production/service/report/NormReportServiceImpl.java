package ua.com.novopacksv.production.service.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.reportModel.NormReport;
import ua.com.novopacksv.production.model.rollModel.OperationType;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.repository.normRepository.NormRepository;
import ua.com.novopacksv.production.repository.productRepository.ProductTypeRepository;
import ua.com.novopacksv.production.service.product.ProductOperationService;
import ua.com.novopacksv.production.service.roll.RollOperationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NormReportServiceImpl implements NormReportService {

    private final ProductTypeRepository productTypeRepository;

    private final RollOperationService rollOperationService;

    private final ProductOperationService productOperationService;

    private final NormRepository normRepository;

    @Override
    public List<NormReport> getAll(LocalDate from, LocalDate to) {
        return productTypeRepository
                .findAll()
                .stream()
                .map(type -> getReport(type, from, to))
                .filter(this::isReportNotEmpty)
                .collect(Collectors.toList());
    }

    private boolean isReportNotEmpty(NormReport report) {
        return (report.getProductPlanAmount() == null)
                ? report.getProductActualAmount() != 0
                : (report.getProductPlanAmount() != 0 || report.getProductActualAmount() != 0);
    }

    private NormReport getReport(ProductType type, LocalDate from, LocalDate to) {
        Norm norm = normRepository.findByProductType_Id(type.getId()).orElse(null);
        Integer actual = getActualAmount(type, from, to);
        List<RollOperation> rollOperations = getRollOperations(type, from, to);
        Integer plan = norm != null ? usedRollActualAmount(rollOperations) * norm.getNorm() : null;
        List<RollOperation> normList = norm != null ? getNullableOperations(norm) : new ArrayList<>();
        rollOperations.addAll(normList);
        NormReport report = new NormReport();
        report.setProductType(type);
        report.setRollOperations(rollOperations);
        report.setProductActualAmount(actual);
        report.setProductPlanAmount(plan);
        return report;
    }

    private List<RollOperation> getRollOperations(ProductType type, LocalDate from, LocalDate to) {
        return rollOperationService.findAll(type, from, to, OperationType.USE);
    }

    private Integer getActualAmount(ProductType type, LocalDate from, LocalDate to) {
        return productOperationService
                .findAllOperationBetweenDatesByTypeId(type.getId(), from, to)
                .stream()
                .filter(operation -> operation.getProductOperationType().equals(ProductOperationType.MANUFACTURED))
                .mapToInt(ProductOperation::getAmount)
                .sum();
    }

    private Integer usedRollActualAmount(List<RollOperation> rollOperations) {
        return rollOperations
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

    private List<RollOperation> getNullableOperations(Norm norm) {
        return norm.getRollTypes()
                .stream()
                .map(type -> {
                    RollOperation operation = new RollOperation();
                    RollManufactured rollManufactured = new RollManufactured();
                    rollManufactured.setRollType(type);
                    operation.setProductType(norm.getProductType());
                    operation.setRollManufactured(rollManufactured);
                    operation.setRollAmount(0);
                    return operation;
                }).collect(Collectors.toList());
    }


}
