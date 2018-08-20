package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.order.OrderItemService;
import ua.com.novopacksv.production.service.product.ProductLeftOverService;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class implements interface {@link ProductPlanLeftoverService} and contains methods for create and count leftovers
 * for ProductPlan
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductPlanLeftoverServiceImpl implements ProductPlanLeftoverService {

    /**
     * An object of service's layer for work with ProductLeftOver
     */
    @Autowired
    @Lazy
    private ProductLeftOverService productLeftOverService;

    /**
     * An object of service's layer for work with ProductPlanOperation
     */
    private final ProductPlanOperationService productPlanOperationService;

    /**
     * An object of service's layer for work with OrderItem
     */
    @Autowired
    @Lazy
    private OrderItemService orderItemService;

    /**
     * An object of service's layer for work with productType
     */
    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    /**
     * Method creates new ProductLeftOver for ProductType's id on date without saving in db. This ProductLeftOver
     * contains a leftover without an amount of planning products for selling from orders
     *
     * @param productTypeId - ProductType's id
     * @param date          - pointed date
     * @return new ProductLeftOver
     */
    @Override
    public ProductLeftOver getOneWithoutPlan(Long productTypeId, LocalDate date) {
        ProductLeftOver tempLeftover = createTempLeftover(productTypeId, date);
        log.debug("Method getOneWithoutPlan(Long productTypeId, LocalDate date): ProductLeftOver for " +
                "ProductType's id = {} on date {} is: {}", productTypeId, date, tempLeftover);
        return tempLeftover;
    }

    /**
     * Method gets all ProductTypes and calls for everyone method getOneWithoutPlan(...)
     *
     * @param date - pointed date
     * @return List of new created ProductLeftOvers
     */
    @Override
    public List<ProductLeftOver> getAllWithoutPlan(LocalDate date) {
        List<ProductType> productTypes = productTypeService.findAll();
        log.debug("Method getAllWithoutPlan(LocalDate date): List<ProductLeftOver> on date {} is creating", date);
        return productTypes.stream()
                .map(productType -> getOneWithoutPlan(productType.getId(), date))
                .collect(Collectors.toList());
    }

    /**
     * Method creates new ProductLeftOver for ProductType's id on date without saving in db. A leftover has a sum of
     * product that is counting from current amount from db minus planning amount from orders and plus planning
     * manufacture
     *
     * @param productTypeId - ProductType's id
     * @param date          - date on which leftover is counting
     * @return new ProductLeftOver
     */
    @Override
    public ProductLeftOver getOneTotal(Long productTypeId, LocalDate date) {
        ProductLeftOver tempLeftOver = createTempLeftover(productTypeId, date);
        Integer amount = tempLeftOver.getAmount() + countAmountOfPlan(productTypeId, date);
        tempLeftOver.setAmount(amount);
        log.debug("Method getOneTotal(Long productTypeId, LocalDate date): ProductLeftOver for ProductType with id = {}" +
                " on date {} was created: {}", productTypeId, date, tempLeftOver);
        return tempLeftOver;
    }

    /**
     * Method gets all ProductTypes and calls for everyone method getOneTotal(....)
     *
     * @param date - pointed date
     * @return List of new created ProductLeftOvers
     */
    @Override
    public List<ProductLeftOver> getAllTotal(LocalDate date) {
        List<ProductType> productTypes = productTypeService.findAll();
        log.debug("Method getAllTotal(LocalDate date): List<ProductLeftOver> on date {} is finding", date);
        return productTypes.stream()
                .map(productType -> getOneTotal(productType.getId(), date))
                .collect(Collectors.toList());
    }

    /**
     * Method counts an amount of not delivered product of one ProductType from OrderItems on some date
     *
     * @param productTypeId - ProductType's id
     * @param date          - pointed date
     * @return sum of not delivered product on pointed date
     */
    private Integer countAmountWithoutPlan(Long productTypeId, LocalDate date) {
        List<OrderItem> orderItems;
        orderItems = orderItemService.findAllNotDelivered(productTypeService.findById(productTypeId), date);
        Integer sum = orderItems.stream().mapToInt(OrderItem::getAmount).sum();
        log.debug("Method countAmountWithoutPlan(Long productTypeId, LocalDate date): Sum of not delivered product" +
                "for ProductType's id = {} on date {} is {}", productTypeId, date, sum);
        return sum;
    }

    /**
     * Method counts an amount of planning manufacture of product from ProductPlanOperations for period from now to the
     * pointed date
     *
     * @param productTypeId - ProductType's id
     * @param toDate        - an end of a period
     * @return sum of products that are planning for manufacture
     */
    private Integer countAmountOfPlan(Long productTypeId, LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations =
                productPlanOperationService.getAll(productTypeId, LocalDate.now(), toDate);
        Integer sum = productPlanOperations.stream().mapToInt(ProductPlanOperation::getProductAmount).sum();
        log.debug("Method countAmountOfPlan(Long productTypeId, LocalDate toDate): Sum of planning for manufacture product " +
                "for ProductType's id = {} from now to {} is {}", productTypeId, toDate, sum);
        return sum;
    }

    /**
     * Method creates new ProductLeftOver for ProductType with pointed id on date
     *
     * @param productTypeId - ProductType's id
     * @param date          - pointed date
     * @return new created ProductLeftOver with counted amount that is different of current amount and sum of not
     * delivered products from orders
     */
    private ProductLeftOver createTempLeftover(Long productTypeId, LocalDate date) {
        ProductLeftOver productLeftOver = productLeftOverService.findByProductTypeId(productTypeId);
        ProductLeftOver tempLeftover = new ProductLeftOver();
        tempLeftover.setProductType(productLeftOver.getProductType());
        tempLeftover.setLeftDate(date);
        tempLeftover.setAmount(productLeftOver.getAmount() - countAmountWithoutPlan(productTypeId, date));
        log.debug("Method createTempLeftover(Long productTypeId, LocalDate date): ProductLeftOver for ProductType's " +
                "id = {} on date  {} was created: {}", productTypeId, date, tempLeftover);
        return tempLeftover;
    }
}
