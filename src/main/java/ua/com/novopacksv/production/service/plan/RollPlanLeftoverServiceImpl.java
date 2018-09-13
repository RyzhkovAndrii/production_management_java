package ua.com.novopacksv.production.service.plan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.roll.RollLeftOverService;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class implements interface {@link RollPlanLeftoverService} and contains methods for count RollLeftOvers
 */
@Service
@Transactional
@Slf4j
public class RollPlanLeftoverServiceImpl implements RollPlanLeftoverService {

    /**
     * An object of service's layer for work with RollPlanOperation
     */
    @Autowired
    @Lazy
    private RollPlanOperationService rollPlanOperationService;

    /**
     * An object of service's layer for work with RollLeftOver
     */
    @Autowired
    @Lazy
    private RollLeftOverService rollLeftOverService;

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
     * Method gets one RollLeftOver for pointed RollTypeId on some date without manufactured plan,
     * but with used plan from ProductPlanOperation
     *
     * @param rollTypeId - RollType's id
     * @param toDate     - date on which it needs to have a RollLeftOver
     * @return RollLeftOver that is not wrote in db
     */
    @Override
    public RollLeftOver getOneWithoutPlan(Long rollTypeId, LocalDate toDate) {
        RollLeftOver tempRollLeftover = createTempRollLeftover(rollTypeId, toDate);
        log.debug("Method getOneWithoutPlan(Long rollTypeId, LocalDate toDate): RollLeftOver for rollType with id = {} " +
                "on date {} was found: {}", rollTypeId, toDate, tempRollLeftover);
        return tempRollLeftover;
    }

    /**
     * Method gets one RollLeftOver for pointed RollTypeId on some date with manufactured and used plan
     *
     * @param rollTypeId - RollType's id
     * @param toDate     - date on which it needs to have a RollLeftOver
     * @return RollLeftOver that is not wrote in db
     */
    @Override
    public RollLeftOver getOneTotal(Long rollTypeId, LocalDate toDate) {
        RollLeftOver tempRollLeftover = createTempRollLeftover(rollTypeId, toDate);
        Integer amount = tempRollLeftover.getAmount() + countAmountOfPlan(rollTypeId, toDate);
        tempRollLeftover.setAmount(amount);
        log.debug("Method getOneTotal(Long rollTypeId, LocalDate toDate): RollLeftOver is {} for RollType with id = {}" +
                "and on date {} ", tempRollLeftover, rollTypeId, toDate);
        return tempRollLeftover;
    }

    /**
     * Method gets leftovers for every RollType on some date without manufactured plan and with used plan
     * from ProductPlanOperations
     *
     * @param toDate -date on which it needs to have a RollLeftOver
     * @return List of RollLeftOvers that are not wrote in db
     */
    @Override
    public List<RollLeftOver> getAllWithoutPlan(LocalDate toDate) {
        List<RollType> rollTypes = rollTypeService.findAll();
        log.debug("Method getAllWithoutPlan(LocalDate toDate): List<RollLeftOver> on date {} are finding", toDate);
        return rollTypes.stream()
                .map(rollType -> getOneWithoutPlan(rollType.getId(), toDate))
                .collect(Collectors.toList());
    }

    /**
     * Method gets leftovers for every RollType on some date with manufactured and used plan
     *
     * @param toDate - date on which it needs to have a RollLeftOver
     * @return List of RollLeftOvers that are not wrote in db
     */
    @Override
    public List<RollLeftOver> getAllTotal(LocalDate toDate) {
        List<RollType> rollTypes = rollTypeService.findAll();
        log.debug("Method getAllTotal(LocalDate toDate): List<RollLeftOver> on date {} are finding", toDate);
        return rollTypes.stream()
                .map(rollType -> getOneTotal(rollType.getId(), toDate))
                .collect(Collectors.toList());
    }

    /**
     * Method creates a leftover for pointed RollType's id on some date without manufactured plan, but with
     * used plan from ProductPlanOperations
     *
     * @param rollTypeId - RollType's id
     * @param toDate     - date on which it needs to have a RollLeftOver
     * @return RollLeftOver that is not wrote in db
     */
    private RollLeftOver createTempRollLeftover(Long rollTypeId, LocalDate toDate) {
        RollLeftOver rollLeftOver =
                rollLeftOverService.findLastRollLeftOverByRollType(rollTypeService.findById(rollTypeId));
        RollLeftOver tempRollLeftover = new RollLeftOver();
        tempRollLeftover.setRollType(rollLeftOver.getRollType());
        tempRollLeftover.setDate(toDate);
        tempRollLeftover.setAmount(rollLeftOver.getAmount() - countAmountWithoutPlan(rollTypeId, toDate));
        log.debug("Method creates TempRollLeftover(Long rollTypeId, LocalDate toDate): RollLeftOver for RollType with " +
                "id = {} on date {} was created: {}", rollTypeId, toDate, tempRollLeftover);
        return tempRollLeftover;
    }

    /**
     * Method counts sum of amount of each roll's used operation without manufactured operation
     *
     * @param rollTypeId - RollType's id
     * @param toDate     - date on which it needs to have a RollLeftOver
     * @return sum of RollAmount from ProductPlanOperations
     */
    private Integer countAmountWithoutPlan(Long rollTypeId, LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations =
                productPlanOperationService.getAllByRollTypeId(rollTypeId, LocalDate.now(), toDate);
        Integer result = productPlanOperations.stream().mapToInt(ProductPlanOperation::getRollAmount).sum();
        log.debug("Method countAmountWithoutPlan(Long rollTypeId, LocalDate toDate): Sum of RollAmounts from " +
                "ProductPlanOperations = {} for RollType's id = {} on date {}", result, rollTypeId, toDate);
        return result;
    }

    /**
     * Method counts sum of RollQuantity from manufactured operations in RollPlanOperation
     *
     * @param rollTypeId - RollType's id
     * @param toDate     - date on which it needs to have a RollLeftOver
     * @return sum of RollQuantity from RollPlanOperation
     */
    private Integer countAmountOfPlan(Long rollTypeId, LocalDate toDate) {
        List<RollPlanOperation> rollPlanOperations = rollPlanOperationService.findAll(rollTypeId, LocalDate.now(), toDate);
        Integer result = rollPlanOperations.stream().mapToInt(RollPlanOperation::getRollQuantity).sum();
        log.debug("Method countAmountOfPlan(Long rollTypeId, LocalDate toDate): Sum of RollQuantity from " +
                "RollPlanOperations = {} for RollType's id = {} on date {}", result, rollTypeId, toDate);
        return result;
    }
}
