package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.order.OrderItemServiceImpl;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class implements interface {@link ProductPlanBatchService} and contains methods for create ProductPlanBatches
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductPlanBatchServiceImpl implements ProductPlanBatchService {

    /**
     * An object of service's layer for work with ProductPlanOperation
     */
    @Autowired
    @Lazy
    private ProductPlanOperationService productPlanOperationService;

    /**
     * An object of service's layer for work with ProductType
     */
    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    /**
     * An object of service's layer for work with OrderItem
     */
    @Autowired
    @Lazy
    private OrderItemServiceImpl orderItemService;

    /**
     * An object of service's layer for work with MachinePlan
     */
    @Autowired
    @Lazy
    private MachinePlanService machinePlanService;

    /**
     * An object of service's layer for work with MachinePlanItem
     */
    @Autowired
    @Lazy
    private MachinePlanItemService machinePlanItemService;

    /**
     * Method creates new ProductPlanBatch for ProductType with pointed id on some date    NOT USED!!!!
     *
     * @param productTypeId - ProductType's id
     * @param date          - pointed date
     * @return new ProductPlanBatch that is not an object of db, used for outside requests for getting all information
     * about manufacturing plan and selling plan of products
     */
    @Override
    public ProductPlanBatch getOne(Long productTypeId, LocalDate date) {
        ProductPlanBatch productPlanBatch = new ProductPlanBatch();
        productPlanBatch.setDate(date);
        ProductType productType = productTypeService.findById(productTypeId);
        productPlanBatch.setProductType(productType);
        productPlanBatch.setManufacturedAmount(countProductPlanManufacturedAmount(productType, date));
        productPlanBatch.setUsedAmount(countProductPlanUsedAmount(productTypeId, date));
        log.debug("Method getOne(Long productTypeId, LocalDate date): ProductPlanBatch for ProductType with id = {} on" +
                "date {} was created: {}", productTypeId, date, productPlanBatch);
        return productPlanBatch;
    }

    /**
     * Method gets all existed ProductTypes from db and create for everyone new ProductPlanBatch by
     * calling method getOne(...)                                       NOT USED!!!
     *
     * @param date - date on which ProductPlanBatches are created
     * @return List of new ProductPlanBatches for all ProductTypes
     */
    @Override
    public List<ProductPlanBatch> getAll(LocalDate date) {
        List<ProductPlanBatch> productPlanBatches = productTypeService.findAll()
                .stream()
                .map((productType) -> getOne(productType.getId(), date))
                .collect(Collectors.toList());
        log.debug("Method getAll(LocalDate date): List<ProductPlanBatch> was created: {}", productPlanBatches);
        return productPlanBatches;
    }

    /**
     * Method gets all existed ProductTypes from db and create for everyone new ProductPlanBatch and ranges its
     *
     * @param fromDate - a beginning of a period
     * @param toDate   - an end of a period
     * @return Map where a key is a ProductType's id and value is a new created ProductPlanBatch
     */
    @Override
    public Map<Long, List<ProductPlanBatch>> getAll(LocalDate fromDate, LocalDate toDate) {
        List<ProductType> productTypes = productTypeService.findAll();
        Map<Long, List<ProductPlanBatch>> batchesMap = productTypes
                .stream()
                .collect(Collectors.toMap(
                        ProductType::getId,
                        v -> getFromRange(v, fromDate, toDate)
                ));
        log.debug("Method getAll(LocalDate fromDate, LocalDate toDate): Map<Long, List<ProductPlanBatch>> was created" +
                " for period from {} to {}", fromDate, toDate);
        return batchesMap;
    }

    /**
     * Method set product and roll amounts of product plans equals for machine plans for product type ID on some date
     *
     * @param productTypeId - ProductType ID
     * @param date          - pointed date
     */
    @Override
    public void equalizePlanToMachinePlan(Long productTypeId, LocalDate date) {
        List<ProductPlanOperation> planOperations = productPlanOperationService.getAll(productTypeId, date, date);
        List<MachinePlan> plans = machinePlanService.findByProductForMachinePlan(productTypeId, date);
        planOperations.forEach(operation -> {
            Integer rollAmount = 0;
            Integer productAmount = 0;
            for (MachinePlan plan : plans) {
                try {
                    MachinePlanItem item = machinePlanItemService.findOne(plan, operation.getRollType());
                    rollAmount += item.getRollAmount();
                    productAmount += item.getProductAmount();
                } catch (ResourceNotFoundException e) {
                    // nothing to do
                }
            }
            operation.setRollAmount(rollAmount);
            operation.setProductAmount(productAmount);
            productPlanOperationService.update(operation);
        });
    }

    /**
     * Method counts an amount of products from ProductPlanOperations for one ProductType on some date
     *
     * @param productType - ProductType
     * @param date        - pointed date
     * @return integer, sum of productAmounts from ProductPlanOperations
     */
    private Integer countProductPlanManufacturedAmount(ProductType productType, LocalDate date) {
        Integer amount = productPlanOperationService
                .getAll(productType.getId(), date, date)
                .stream()
                .mapToInt(ProductPlanOperation::getProductAmount)
                .sum();
        log.debug("Method countProductPlanManufacturedAmount(Long productTypeId, LocalDate date): Sum of productAmounts" +
                " from ProductPlanOperations is {} for productType's id = {} on date {}", amount, productType, date);
        return amount;
    }

    /**
     * Method counts sum of amounts from OrderItems for one ProductType on some date
     *
     * @param productTypeId - ProductType's id
     * @param date          - pointed date
     * @return integer, sum of amounts from OrderItems
     */
    private Integer countProductPlanUsedAmount(Long productTypeId, LocalDate date) {
        Integer amount = orderItemService.findAllNotDelivered(productTypeService.findById(productTypeId), date)
                .stream()
                .mapToInt(OrderItem::getAmount)
                .sum();
        log.debug("Method countProductPlanUsedAmount(Long productTypeId, LocalDate date): Sum of amounts is {} " +
                "for ProductType's id = {} on date {}", amount, productTypeId, date);
        return amount;
    }

    /**
     * Method creates new ProductPlanBatch for one ProductType on some date
     *
     * @param productType - ProductType
     * @param date        - pointed date
     * @return new ProductPlanBatch
     */
    private ProductPlanBatch getOne(ProductType productType, LocalDate date) {
        Integer manufacturedAmount = countProductPlanManufacturedAmount(productType, date);
        Integer usedAmount = countProductPlanUsedAmount(productType.getId(), date);
        if (manufacturedAmount == 0 && usedAmount == 0) {
            log.debug("Method getOne(ProductType productType, LocalDate date): There are not any planning operation for " +
                    "this product type {} on date {}, ProductPlanBatch is null", productType, date);
            return null;
        }

        ProductPlanBatch productPlanBatch = new ProductPlanBatch();
        productPlanBatch.setDate(date);
        productPlanBatch.setProductType(productType);
        productPlanBatch.setManufacturedAmount(manufacturedAmount);
        productPlanBatch.setUsedAmount(usedAmount);
        log.debug("Method getOne(ProductType productType, LocalDate date): ProductPlanBatch for ProductType {} " +
                "on date {} was created: {}", productType, date, productPlanBatch);
        return productPlanBatch;
    }

    /**
     * Method creates and ranges List of ProductPlanBatches for one ProductType for a period
     *
     * @param productType - pointed ProductType
     * @param fromDate    - a beginning of the period
     * @param toDate      - an end of the period
     * @return ranged List of  created ProductPlanBatches
     */
    private List<ProductPlanBatch> getFromRange(ProductType productType, LocalDate fromDate, LocalDate toDate) {
        log.debug("Method getFromRange(ProductType productType, LocalDate fromDate, LocalDate toDate): " +
                        "List<ProductPlanBatch> for productType {} for period from {} to {} is finding", productType,
                fromDate, toDate);
        return Stream
                .iterate(fromDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(fromDate, toDate))
                .map(date -> getOne(productType, date))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
