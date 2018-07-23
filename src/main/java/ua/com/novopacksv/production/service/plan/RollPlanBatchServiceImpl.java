package ua.com.novopacksv.production.service.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.planModel.RollPlanBatch;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RollPlanBatchServiceImpl implements RollPlanBatchService {

    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    @Autowired
    @Lazy
    private ProductPlanOperationService productPlanOperationService;

    @Autowired
    @Lazy
    private RollPlanOperationService rollPlanOperationService;

    @Override
    public RollPlanBatch getOne(Long rollTypeId, LocalDate date) {
        RollPlanBatch rollPlanBatch = new RollPlanBatch();
        rollPlanBatch.setDate(date);
        rollPlanBatch.setRollType(rollTypeService.findById(rollTypeId));
        rollPlanBatch.setRollPlanUsedAmount(countRollPlanUsedAmount(rollTypeId, date));
        rollPlanBatch.setRollPlanManufacturedAmount(countRollPlanManufacturedAmount(rollTypeId, date));
        return rollPlanBatch;
    }

    @Override
    public List<RollPlanBatch> getAll(LocalDate date) {
        return rollTypeService.findAll().stream().map(rollType -> getOne(rollType.getId(), date))
                .collect(Collectors.toList());
    }

    private Integer countRollPlanUsedAmount(Long rollTypeId, LocalDate date) {
        return productPlanOperationService.getAllByRollTypeId(rollTypeId, date, date).stream()
                .mapToInt((planOperation) -> planOperation.getRollAmount()).sum();
    }

    private Integer countRollPlanManufacturedAmount(Long rollTypeId, LocalDate date){
        return rollPlanOperationService.findAll(rollTypeId, date, date)
                .stream()
                .mapToInt(RollPlanOperation::getRollQuantity)
                .sum();
    }
}