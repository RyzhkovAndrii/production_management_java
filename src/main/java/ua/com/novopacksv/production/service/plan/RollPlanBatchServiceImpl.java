package ua.com.novopacksv.production.service.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.planModel.RollPlanBatch;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Override
    public Map<Long, List<RollPlanBatch>> getAll(LocalDate fromDate, LocalDate toDate) {
        List<RollType> rollTypes = rollTypeService.findAll();
        Map<Long, List<RollPlanBatch>> rollBatchesMap = rollTypes
                .stream()
                .collect(Collectors.toMap(RollType::getId, v -> getFromRange(v, fromDate, toDate)));
        return rollBatchesMap;
    }

    private Integer countRollPlanUsedAmount(Long rollTypeId, LocalDate date) {
        return productPlanOperationService.getAllByRollTypeId(rollTypeId, date, date).stream()
                .mapToInt((planOperation) -> planOperation.getRollAmount()).sum();
    }

    private List<RollPlanBatch> getFromRange(RollType rollType, LocalDate fromDate, LocalDate toDate){
        List<RollPlanBatch> rollPlanBatches = new ArrayList<>();
        LocalDate date = LocalDate.from(fromDate);
        do {
            RollPlanBatch planBatch = getOne(rollType.getId(), date);
            if (planBatch != null) {
                rollPlanBatches.add(planBatch);
            }
        } while ((date = date.plusDays(1)).isBefore(toDate));
        return rollPlanBatches;
    }

    private Integer countRollPlanManufacturedAmount(Long rollTypeId, LocalDate date){
        return rollPlanOperationService.findAll(rollTypeId, date, date)
                .stream()
                .mapToInt(RollPlanOperation::getRollQuantity)
                .sum();
    }
}
