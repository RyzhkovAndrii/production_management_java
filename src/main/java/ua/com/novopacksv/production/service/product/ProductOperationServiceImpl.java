package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NegativeAmountException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.productRepository.ProductOperationRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * The class implements interface {@link ProductOperationService}
 * contains logic for work with product's operations
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductOperationServiceImpl implements ProductOperationService {

    /**
     * An object of repository layer for have access to methods of work with DB
     */
    private final ProductOperationRepository productOperationRepository;

    /**
     * An object of service layer for have access to methods of work with product's leftover
     */
    private final ProductLeftOverService productLeftOverService;

    /**
     * An object of service layer for have access to methods of work with product type
     */
    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    /**
     * The method finds product operation by it's id
     *
     * @param id - product operation's id
     * @return product operation with pointed id
     * @throws ResourceNotFoundException if there is not operation with pointed id
     */
    @Override
    @Transactional(readOnly = true)
    public ProductOperation findById(Long id) throws ResourceNotFoundException {
        ProductOperation productOperation = productOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Product operation with id = %d was not found", id);
            log.error("Method findById(Long id): product operation with id = {} was not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): product operation {} with id = {} was found", productOperation, id);
        return productOperation;
    }

    /**
     * Method finds all product operations
     *
     * @return list of all product operations
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOperation> findAll() {
        List<ProductOperation> productOperations = productOperationRepository.findAll();
        log.debug("Method findAll(): all product operations was found");
        return productOperations;
    }

    /**
     * Method save product operation. Before saving it changes leftover for this product type
     *
     * @param productOperation - product operation for save
     * @return saved product operation
     * @throws ResourceNotFoundException if there is not product type of that operation
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductOperation save(ProductOperation productOperation) throws ResourceNotFoundException {
        changingLeftOver(productOperation, getChangingAmount(productOperation));
        log.debug("Method save(ProductOperation productOperation): product operation {} is saving", productOperation);
        return productOperationRepository.save(productOperation);
    }

    /**
     * Method updates product operation and changes product's leftover
     *
     * @param productOperation - product operation for update
     * @return updated product operation
     * @throws ResourceNotFoundException if there is not this product operation in db
     */
    @Override
    public ProductOperation update(ProductOperation productOperation) throws ResourceNotFoundException {
        ProductOperation productOperationOld = findById(productOperation.getId());
        changingLeftOver(productOperation, productOperation.getAmount() - productOperationOld.getAmount());
        log.debug("Method update(ProductOperation productOperation): product operation {} is updating", productOperation);
        return productOperationRepository.save(productOperation);
    }

    /**
     * Method tests if product operation with pointed id exists, changes product's leftover and delete that product
     * operation
     *
     * @param id - product operation's id
     * @throws ResourceNotFoundException if there is not product operation with pointed id
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        ProductOperation productOperation = findById(id);
        changingLeftOver(productOperation, -getChangingAmount(productOperation));
        productOperationRepository.delete(productOperation);
        log.debug("Method delete(Long id): product operation {} with id {} was deleted", productOperation, id);
    }

    /**
     * Method finds all operations for pointed product type for a period
     *
     * @param productTypeId - product type's id
     * @param fromDate      - date of period's beginning
     * @param toDate        - date of period's end
     * @return list of product operations for one product type for a period
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductOperation> findAllOperationBetweenDatesByTypeId(Long productTypeId, LocalDate fromDate,
                                                                       LocalDate toDate) {
        ProductType productType = productTypeService.findById(productTypeId);
        log.debug("Method findAllOperationBetweenDatesByTypeId(Long productTypeId, LocalDate fromDate,LocalDate toDate):" +
                " all operations for product type {} for period from {} to {} were found", productType, fromDate, toDate);
        return productOperationRepository.findAllByProductTypeAndDateBetween(productType, fromDate, toDate);
    }

    /**
     * Method return positive for {@code ProductOperationType.MANUFACTURED} or negative for
     * {@code ProductOperationType.SOLD} amount of operation
     *
     * @return amount of operation with the  positive or negative sign
     */
    private Integer getChangingAmount(ProductOperation productOperation) {
        Integer amount = 0;
        if (productOperation.getProductOperationType().equals(ProductOperationType.SOLD)) {
            amount -= productOperation.getAmount();
        } else {
            amount += productOperation.getAmount();
        }
        log.debug("Method getChangingAmount(ProductOperation productOperation): amount of product operation {} is {}",
                productOperation, amount);
        return amount;
    }

    /**
     * Method changes product's leftover for operation
     *
     * @param productOperation - operation that changes leftover
     * @param changingAmount   - amount for change
     * @throws ResourceNotFoundException if leftover doesn't exist in db
     */
    private void changingLeftOver(ProductOperation productOperation, Integer changingAmount)
            throws ResourceNotFoundException {
        ProductLeftOver productLeftOver =
                productLeftOverService.findByProductTypeId(productOperation.getProductType().getId());
        Integer leftOverAmount = productLeftOver.getAmount();
        if ((leftOverAmount + changingAmount) > 0 || (leftOverAmount + changingAmount) == 0) {
            productLeftOver.setAmount(leftOverAmount + changingAmount);
            productLeftOverService.update(productLeftOver);
            log.debug("Method changingLeftOver(ProductOperation productOperation, Integer changingAmount):" +
                            "leftover {} for product operation {} was changed on amount {}", productLeftOver,
                    productOperation, changingAmount);
        } else {
            log.error("Method changingLeftOver(ProductOperation productOperation, Integer changingAmount):" +
                    "leftover {} for product operation {} can't be changed on amount {} because of negative " +
                    "leftover", productLeftOver, productOperation, changingAmount);
            throw new NegativeAmountException("Product's leftover is negative!");
        }
    }
}