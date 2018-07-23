package ua.com.novopacksv.production.service.plan;

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

@Service
@Transactional
public class RollPlanLeftoverServiceImpl implements RollPlanLeftoverService {

    @Autowired
    @Lazy
    private RollPlanOperationService rollPlanOperationService;

    @Autowired
    @Lazy
    private RollLeftOverService rollLeftOverService;

    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    @Autowired
    @Lazy
    private ProductPlanOperationService productPlanOperationService;

    @Override
    public RollLeftOver getOneWithoutPlan(Long rollTypeId, LocalDate toDate) {
        RollLeftOver tempRollLeftover = createTempRollLeftover(rollTypeId, toDate);
        return tempRollLeftover;
    }

    @Override
    public RollLeftOver getOneTotal(Long rollTypeId, LocalDate toDate) {
        RollLeftOver tempRollLeftover = createTempRollLeftover(rollTypeId, toDate);
        Integer amount = tempRollLeftover.getAmount() + countAmountOfPlan(rollTypeId, toDate);
        tempRollLeftover.setAmount(amount);
        return tempRollLeftover;
    }

    @Override
    public List<RollLeftOver> getAllWithoutPlan(LocalDate toDate) {
        List<RollType> rollTypes = rollTypeService.findAll();
        return rollTypes.stream()
                .map(rollType -> getOneWithoutPlan(rollType.getId(), toDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<RollLeftOver> getAllTotal(LocalDate toDate) {
        List<RollType> rollTypes = rollTypeService.findAll();
        return rollTypes.stream()
                .map(rollType -> getOneTotal(rollType.getId(), toDate))
                .collect(Collectors.toList());
    }

    private RollLeftOver createTempRollLeftover(Long rollTypeId, LocalDate toDate) {
        RollLeftOver rollLeftOver =
                rollLeftOverService.findLastRollLeftOverByRollType(rollTypeService.findById(rollTypeId));
        RollLeftOver tempRollLeftover = new RollLeftOver();
        tempRollLeftover.setRollType(rollLeftOver.getRollType());
        tempRollLeftover.setDate(toDate);
        tempRollLeftover.setAmount(rollLeftOver.getAmount() - countAmountWithoutPlan(rollTypeId, toDate));
        return tempRollLeftover;
    }

    private Integer countAmountWithoutPlan(Long rollTypeId, LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations =
                productPlanOperationService.getAllByRollTypeId(rollTypeId, LocalDate.now(), toDate);
        return productPlanOperations.stream().mapToInt(ProductPlanOperation::getRollAmount).sum();
    }

    private Integer countAmountOfPlan(Long rollTypeId, LocalDate toDate) {
        List<RollPlanOperation> rollPlanOperations = rollPlanOperationService.findAll(rollTypeId, LocalDate.now(), toDate);
        return rollPlanOperations.stream().mapToInt(RollPlanOperation::getRollQuantity).sum();
    }
}
