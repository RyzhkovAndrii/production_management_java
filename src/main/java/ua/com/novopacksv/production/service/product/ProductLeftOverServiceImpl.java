package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NegativeAmountException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.productRepository.ProductLeftOverRepository;
import ua.com.novopacksv.production.service.order.OrderItemService;
import ua.com.novopacksv.production.service.order.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class implements interface {@link ProductLeftOverService}, contains logic for work with products' leftovers
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductLeftOverServiceImpl implements ProductLeftOverService {

    /**
     * An object of repository layer for have access to methods of work with DB
     */
    private final ProductLeftOverRepository productLeftOverRepository;

    /**
     * An object of service layer for have access to methods of work with product operations
     */
    @Autowired
    @Lazy
    private ProductOperationService productOperationService;

    /**
     * An object of service layer for have access to methods of work with order items
     */
    @Autowired
    @Lazy
    private OrderItemService orderItemService;

    /**
     * An object of service layer for have access to methods of work with orders
     */
    @Autowired
    @Lazy
    private OrderService orderService;

    /**
     * Method finds all leftovers on pointed date
     *
     * @param date - date on witch leftovers are finding
     * @return list of leftovers
     */
    @Override
    public List<ProductLeftOver> findOnDate(LocalDate date) {
        List<ProductLeftOver> productLeftOvers = productLeftOverRepository.findAll();
        List<ProductLeftOver> productLeftOversOnDate = productLeftOvers.stream()
                .map((productLeftOver) -> getLeftOverOnDate(date, productLeftOver)).collect(Collectors.toList());
        log.debug("Method findOnDate (LocalDate date): List of leftovers on date {} was found: {}", date,
                productLeftOversOnDate);
        return productLeftOversOnDate;
    }

    /**
     * Method finds leftover on the latest date of delivery
     *
     * @return list of product leftovers
     */
    @Override
    public List<ProductLeftOver> findLatest() {
        LocalDate latestDeliveryDate = orderService.findMaxDeliveryDate();
        List<ProductLeftOver> productLeftOvers = findOnDate(latestDeliveryDate);
        log.debug("Method findLatest(): leftovers on the latest delivery date {} were found: {}", latestDeliveryDate,
                productLeftOvers);
        return productLeftOvers;
    }

    /**
     * Method finds leftover for one product type on pointed date
     *
     * @param productTypeId - product type's id
     * @param date          - date on witch leftover is finding
     * @return product's leftover on date
     * @throws ResourceNotFoundException if leftover doesn't exist in db
     */
    @Override
    public ProductLeftOver findByProductType_IdOnDate(Long productTypeId, LocalDate date)
            throws ResourceNotFoundException {
        ProductLeftOver productLeftOver =
                productLeftOverRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
                    String message = String.format("Leftover for product type with Id = %d was not found", productTypeId);
                    log.error("Method findByProductType_IdOnDate(Long productTypeId, LocalDate date): " +
                            "Leftover for product type with id = {} was not found", productTypeId);
                    return new ResourceNotFoundException(message);
                });
        ProductLeftOver productLeftOverOnDate = getLeftOverOnDate(date, productLeftOver);
        log.debug("Method findByProductType_IdOnDate(Long productTypeId, LocalDate date): Leftover for product type " +
                "with id = {} was found: {}", productTypeId, productLeftOverOnDate);
        return productLeftOverOnDate;
    }

    /**
     * Method finds product leftover by id
     *
     * @param id - product leftover's id
     * @return leftover
     * @throws ResourceNotFoundException if product leftover with this id doesn't exist
     */
    @Override
    public ProductLeftOver findById(Long id) throws ResourceNotFoundException {
        ProductLeftOver productLeftOver = productLeftOverRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Product leftover with id = %d was not found", id);
            log.error("Method findById(Long id): leftover with id = {} was not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): leftover with id = {} was found: {}", id, productLeftOver);
        return productLeftOver;
    }

    /**
     * Method finds leftover by product type's id
     *
     * @param productTypeId - product type's id
     * @return product's leftover
     * @throws ResourceNotFoundException if product's leftover doesn't exist
     */
    @Override
    public ProductLeftOver findByProductTypeId(Long productTypeId) throws ResourceNotFoundException {
        ProductLeftOver productLeftOver = productLeftOverRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
            String message = String.format("Product type with id = %d was not found", productTypeId);
            log.error("Method findByProductTypeId(Long productTypeId): leftover of product type with id ={} was not found",
                    productTypeId);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findByProductTypeId(Long productTypeId): leftover of product type with id ={} was found: {}",
                productTypeId, productLeftOver);
        return productLeftOver;
    }

    /**
     * Method finds all product leftovers
     *
     * @return list of leftovers
     */
    @Override
    public List<ProductLeftOver> findAll() {
        List<ProductLeftOver> productLeftOvers = productLeftOverRepository.findAll();
        log.debug("Method findAll(): All leftovers were found");
        return productLeftOvers;
    }

    /**
     * Method saves product's leftover
     *
     * @param productLeftOver - product's leftover for save
     * @return saved product's leftover
     */
    @Override
    public ProductLeftOver save(ProductLeftOver productLeftOver) {
        log.debug("Method save(ProductLeftOver productLeftOver): product's leftover {} are saving", productLeftOver);
        return productLeftOverRepository.save(productLeftOver);
    }

    /**
     * Method tests if exist and updates product's leftover
     *
     * @param productLeftOver - leftover for update
     * @return updated leftover
     */
    @Override
    public ProductLeftOver update(ProductLeftOver productLeftOver) throws ResourceNotFoundException {
        findById(productLeftOver.getId());
        log.debug("Method update(ProductLeftOver productLeftOver): product's leftover {} are updating", productLeftOver);
        return productLeftOverRepository.save(productLeftOver);
    }

    /**
     * Method tests if exist and deletes product's leftover by it's id
     *
     * @param id - leftover's id
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        ProductLeftOver productLeftOver = findById(id);
        productLeftOverRepository.deleteById(id);
        log.debug("Method delete(Long id): product's leftover with id = {} was found: {}", id, productLeftOver);
    }

    /**
     * Method returns leftover on date. This leftover doesn't input in db
     *
     * @param date            - date on witch leftover is finding
     * @param productLeftOver - current leftover from db
     * @return leftover on pointed date
     */
    private ProductLeftOver getLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) {
        ProductLeftOver productLeftOverTemp = new ProductLeftOver();
        productLeftOverTemp.setLeftDate(date);
        productLeftOverTemp.setProductType(productLeftOver.getProductType());
        productLeftOverTemp.setAmount(amountOfLeftOverOnDate(date, productLeftOver));
        log.debug("Method gatLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver): leftover on date {} " +
                "was found: {}", date, productLeftOverTemp);
        return productLeftOverTemp;
    }

    /**
     * Method save new leftover for new product type
     *
     * @param productType - new product type
     * @return new leftover
     */
    @Override
    public ProductLeftOver saveByProductType(ProductType productType) {
        ProductLeftOver productLeftOver = createNewLeftOver(productType);
        log.debug("Method saveByProductType(ProductType productType): for product type {} new leftover was created: {}",
                productType, productLeftOver);
        return productLeftOver;
    }

    /**
     * Method determines if operation type is SOLD
     *
     * @param productOperation - operation for determine
     * @return true if operation type is SOLD and false if no
     */
    public Boolean isSoldOperation(ProductOperation productOperation) {
        Boolean result = productOperation.getProductOperationType().equals(ProductOperationType.SOLD);
        log.debug("Method isSoldOperation(ProductOperation productOperation): is operation type SOLD for operation {} " +
                "is determined as {}", productOperation, result);
        return result;
    }

    /**
     * Method returns an amount of leftover on date
     *
     * @param date            - pointed date
     * @param productLeftOver - current leftover from db
     * @return amount of leftovers on date
     */
    private Integer amountOfLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) {
        log.debug("Method amountOfLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) in process");
        if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
            return countAmountOfLeftOverForPast(date, productLeftOver);
        } else {
            return countAmountOfLeftOverForFuture(date, productLeftOver);
        }
    }

    /**
     * Method counts amount of product's leftover on past date
     *
     * @param date            - previous date
     * @param productLeftOver - current product's leftover from db
     * @return integer amount of product's leftover on date
     */
    private Integer countAmountOfLeftOverForPast(LocalDate date, ProductLeftOver productLeftOver) {
        List<ProductOperation> operationsBetweenDates =
                productOperationService.findAllOperationBetweenDatesByTypeId(productLeftOver.getProductType().getId(),
                        date.plusDays(1), LocalDate.now());
        Integer amount = productLeftOver.getAmount();
        for (ProductOperation productOperation : operationsBetweenDates) {
            amount += isSoldOperation(productOperation) ?
                    productOperation.getAmount() : -productOperation.getAmount();
        }
        if (amount >= 0) {
            log.debug("Method countAmountOfLeftOverForPast(LocalDate date, ProductLeftOver productLeftOver): Amount of " +
                    "leftover is {} on date {} for current leftover {}", amount, date, productLeftOver);
            return amount;
        } else {
            log.error("Method countAmountOfLeftOverForPast(LocalDate date, ProductLeftOver productLeftOver): Amount of" +
                    "leftover is negative on date {} for current leftover {}", date, productLeftOver);
            throw new NegativeAmountException("Product's leftover can't be negative!");
        }
    }

    /**
     * Method counts amount of product's leftover for future date from undelivered orders
     *
     * @param date            - future date
     * @param productLeftOver - current product's leftover
     * @return - integer amount of planning leftover on future date
     */
    private Integer countAmountOfLeftOverForFuture(LocalDate date, ProductLeftOver productLeftOver) {
        ProductType productType = productLeftOver.getProductType();
        List<OrderItem> orderItems = orderItemService.findAllNotDelivered(productType, date);
        Integer amount = productLeftOver.getAmount();
        for (OrderItem orderItem : orderItems) {
            amount -= orderItem.getAmount();
        }
        log.debug("Method countAmountOfLeftOverForFuture(LocalDate date, ProductLeftOver productLeftOver): " +
                "Amount of leftover on date {} is {} for current leftover {}", date, amount, productLeftOver);
        return amount;
    }

    /**
     * Method create and save new leftover for new product
     *
     * @param productType - new product type
     * @return new saved product's leftover
     */
    private ProductLeftOver createNewLeftOver(ProductType productType) {
        ProductLeftOver productLeftOver = new ProductLeftOver();
        productLeftOver.setProductType(productType);
        productLeftOver.setAmount(0);
        productLeftOver.setLeftDate(LocalDate.now());
        log.debug("Method createNewLeftOver(ProductType productType): new leftover {} was created for product type {}",
                productLeftOver, productType);
        return productLeftOverRepository.save(productLeftOver);
    }
}
