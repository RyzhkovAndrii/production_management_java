package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
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

@Service
@Transactional
@RequiredArgsConstructor
public class ProductBatchServiceImpl implements ProductBatchService {

    private final ProductOperationService productOperationService;
    private final ProductTypeService productTypeService;

    @Override
    public ProductBatch getOne(Long productTypeId, LocalDate date) throws ResourceNotFoundException {
        return getOne(productTypeId, date, date);
    }

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
        return productBatch;
    }

    @Override
    public List<ProductBatch> getAll(LocalDate date) {
        return getAll(date, date);
    }

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
        return productBatches;
    }

    private Boolean isManufacturedOperation(ProductOperation productOperation) {
        return productOperation.getProductOperationType().equals(ProductOperationType.MANUFACTURED);
    }

}