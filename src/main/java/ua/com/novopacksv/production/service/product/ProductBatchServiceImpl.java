package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductBatch;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The class implements interface {@link ProductBatchService} and contains a logic for get product's batches for
 * pointed day or some period from product operations
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductBatchServiceImpl implements ProductBatchService {

    /**
     * An object of service layer for have access to methods for work with product operations
     */
    private final ProductOperationService productOperationService;
    /**
     * An object of service layer for have access to methods for work with product types
     */
    private final ProductTypeService productTypeService;

    /**
     * The method find one product batch for one product type for one date
     *
     * @param productTypeId - searching product type's id
     * @param date          - pointed date
     * @return one Product Batch
     * @throws ResourceNotFoundException if there is not product type with pointed id
     */
    @Override
    public ProductBatch getOne(Long productTypeId, LocalDate date) throws ResourceNotFoundException {
        ProductBatch productBatch = getOne(productTypeId, date, date);
        log.debug("Method getOne(Long productTypeId, LocalDate date): Product batch for productTypeId= {} on date {} " +
                "was found: {}", productTypeId, date, productBatch);
        return productBatch;
    }

    /**
     * The method find one product batch for one product type for a period
     *
     * @param productTypeId - searching product type's id
     * @param fromDate      - beginning of a period
     * @param toDate        - tnd of period
     * @return one Product Batch
     * @throws ResourceNotFoundException if there is not product type with pointed id
     */
    @Override
    public ProductBatch getOne(Long productTypeId, LocalDate fromDate, LocalDate toDate)
            throws ResourceNotFoundException {
        ProductBatch productBatch = new ProductBatch();
        List<ProductOperation> productOperations =
                productOperationService.findAllOperationBetweenDatesByTypeId(productTypeId, fromDate, toDate);
        Integer manufacturedAmount = 0;
        Integer soldAmount = 0;
        for (ProductOperation po : productOperations) {
            if (isManufacturedOperation(po)) {
                manufacturedAmount += po.getAmount();
            } else {
                soldAmount += po.getAmount();
            }
        }
        productBatch.setProductType(productTypeService.findById(productTypeId));
        productBatch.setManufacturedAmount(manufacturedAmount);
        productBatch.setSoldAmount(soldAmount);
        log.debug("Method getOne(Long productTypeId, LocalDate fromDate, LocalDate toDate): ProductBatch for " +
                        "product type with id = {} for period from {} to {} was found: {}", productTypeId, fromDate,
                toDate, productBatch);
        return productBatch;
    }

    /**
     * Method finds product batches for all product types on pointed date
     *
     * @param date - pointed date
     * @return list of product batches
     */
    @Override
    public List<ProductBatch> getAll(LocalDate date) {
        log.debug("Method getAll(LocalDate date): List of ProductBatches are finding on date {}", date);
        return getAll(date, date);
    }

    /**
     * Method finds product batches for all product types for a peroid
     *
     * @param fromDate - beginning of a period
     * @param toDate   - end of a period
     * @return list of product batches
     */
    @Override
    public List<ProductBatch> getAll(LocalDate fromDate, LocalDate toDate) {
        List<ProductType> productTypes = productTypeService.findAll();
        List<ProductBatch> productBatches = new ArrayList<>();
        for (ProductType productType : productTypes) {
            ProductBatch productBatch = getOne(productType.getId(), fromDate, toDate);
            if (productBatch != null) {
                productBatches.add(productBatch);
            }
        }
        log.debug("Method getAll(LocalDate fromDate, LocalDate toDate): Product bathes for a period from {} to {} " +
                "are found", fromDate, toDate);
        return productBatches;
    }

    /**
     * Method checks if product operation's type is MANUFACTURED
     *
     * @param productOperation - operation for check
     * @return true if operation is manufactured and false if it is not
     */
    private Boolean isManufacturedOperation(ProductOperation productOperation) {
        Boolean result = productOperation.getProductOperationType().equals(ProductOperationType.MANUFACTURED);
        log.debug("Method isManufacturedOperation(ProductOperation productOperation): Product operation {} " +
                "was determined as Manufactured operation with a result {}", productOperation, result);
        return result;
    }
}