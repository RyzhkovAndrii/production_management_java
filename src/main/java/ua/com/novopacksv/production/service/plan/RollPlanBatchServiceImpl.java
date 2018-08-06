package ua.com.novopacksv.production.service.plan;

import lombok.extern.slf4j.Slf4j;
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

/**
 * Class implements interface {@link RollPlanBatchService} and contains methods that create new RollBatch
 * and count amount for it
 */
@Service
@Transactional
@Slf4j
public class RollPlanBatchServiceImpl implements RollPlanBatchService {

    /**
     * An object of service's layer for work with RollType
     */
    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    /**
     * An object of service's layer for work with ProductPlanOperation
     */
    @Autowired
    @Lazy
    private ProductPlanOperationService productPlanOperationService;

    /**
     * An object of service's layer for work with RollPlanOperation
     */
    @Autowired
    @Lazy
    private RollPlanOperationService rollPlanOperationService;

    /**
     * Method creates a new RollPlanBatch for one RollType on some date
     *
     * @param rollTypeId - RollType's Id
     * @param date       - pointed date
     * @return RollPlanBatch for a RollType on some date with amount of plan for use and for manufacture
     */
    @Override
    public RollPlanBatch getOne(Long rollTypeId, LocalDate date) {
        RollPlanBatch rollPlanBatch = new RollPlanBatch();
        rollPlanBatch.setDate(date);
        rollPlanBatch.setRollType(rollTypeService.findById(rollTypeId));
        rollPlanBatch.setRollPlanUsedAmount(countRollPlanUsedAmount(rollTypeId, date));
        rollPlanBatch.setRollPlanManufacturedAmount(countRollPlanManufacturedAmount(rollTypeId, date));
        log.debug("Method getOne(Long rollTypeId, LocalDate date): RollPlanBatch for RollTypeId = {} on date {}" +
                "was found: {}", rollTypeId, date, rollPlanBatch);
        return rollPlanBatch;
    }

    /**
     * Metod creates RollPlanBatches for all RollTypes on some date
     *
     * @param date - pointed date
     * @return List of RollPlanBatches
     */
    @Override
    public List<RollPlanBatch> getAll(LocalDate date) {
        log.debug("Method getAll(LocalDate date): List of RollPlanBatches on date {} is finding", date);
        return rollTypeService.findAll().stream().map(rollType -> getOne(rollType.getId(), date))
                .collect(Collectors.toList());
    }

    /**
     * Method create ranged RollPlanBatches for all RollTypes in interval of dates
     *
     * @param fromDate - a beginning of interval
     * @param toDate   - an end of interval
     * @return Map with RollType's id as a key and List of RollPlanBatches for every RollType as value
     */
    @Override
    public Map<Long, List<RollPlanBatch>> getAll(LocalDate fromDate, LocalDate toDate) {
        List<RollType> rollTypes = rollTypeService.findAll();
        Map<Long, List<RollPlanBatch>> rollBatchesMap = rollTypes
                .stream()
                .collect(Collectors.toMap(RollType::getId, v -> getFromRange(v, fromDate, toDate)));
        log.debug("Method getAll(LocalDate fromDate, LocalDate toDate): Map<Long, List<RollPlanBatch>> from" +
                "date {} to date {} was found: {}", fromDate, toDate, rollTypes);
        return rollBatchesMap;
    }

    /**
     * Method counts quantity of rolls for plan's use from ProductPlanOperations on date
     *
     * @param rollTypeId - RollType's id
     * @param date       - date for count
     * @return quantity of rolls for plan's use as integer
     */
    private Integer countRollPlanUsedAmount(Long rollTypeId, LocalDate date) {
        Integer result = productPlanOperationService.getAllByRollTypeId(rollTypeId, date, date).stream()
                .mapToInt((planOperation) -> planOperation.getRollAmount()).sum();
        log.debug("Method countRollPlanUsedAmount(Long rollTypeId, LocalDate date): Quantity of rolls for" +
                "plan's use is {} for rollTypeId = {} on date {}", result, rollTypeId, date);
        return result;
    }

    /**
     * Method ranges RollPlanBatches by date
     *
     * @param rollType - RollType for which RollPlanBatches are counting
     * @param fromDate - a beginning of interval
     * @param toDate   - an end of interval
     * @return ranged List of RollPlanBatches for pointed rollType for interval of dates
     */
    private List<RollPlanBatch> getFromRange(RollType rollType, LocalDate fromDate, LocalDate toDate) {
        List<RollPlanBatch> rollPlanBatches = new ArrayList<>();
        LocalDate date = LocalDate.from(fromDate);
        do {
            RollPlanBatch planBatch = getOne(rollType.getId(), date);
            if (planBatch != null) {
                rollPlanBatches.add(planBatch);
            }
        } while ((date = date.plusDays(1)).isBefore(toDate));
        log.debug("Method getFromRange(RollType rollType, LocalDate fromDate, LocalDate toDate): List of" +
                "RollPlanBatches for rollType {} from {} to {} was found", rollType, fromDate, toDate);
        return rollPlanBatches;
    }

    /**
     * Method counts quantity of rolls that are planed for manufacture on some date
     *
     * @param rollTypeId - RollType's id
     * @param date       - date for count
     * @return quantity of rolls that are planed for manufacture for one RollType
     */
    private Integer countRollPlanManufacturedAmount(Long rollTypeId, LocalDate date) {
        Integer result = rollPlanOperationService.findAll(rollTypeId, date, date)
                .stream()
                .mapToInt(RollPlanOperation::getRollQuantity)
                .sum();
        log.debug("Method countRollPlanManufacturedAmount(Long rollTypeId, LocalDate date): Quantity of rolls that" +
                " are planed for manufacture is {} for RollTypeId = {} on date {}", result, rollTypeId, date);
        return result;
    }
}
