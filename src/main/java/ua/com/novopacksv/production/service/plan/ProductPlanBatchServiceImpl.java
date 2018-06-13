package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductPlanBatchServiceImpl implements ProductPlanBatchService {

    @Autowired
    @Lazy
    private ProductPlanOperationService productPlanOperationService;

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Override
    public ProductPlanBatch getOne(Long productTypeId, LocalDate date) {
        ProductPlanBatch productPlanBatch = new ProductPlanBatch();
        productPlanBatch.setDate(date);
        productPlanBatch.setProductType(productTypeService.findById(productTypeId));
        productPlanBatch.setAmount(countProductPlanAmount(productTypeId, date));
        return productPlanBatch;
    }

    @Override
    public List<ProductPlanBatch> getAll(LocalDate date) {
        List<ProductType> productTypes = productTypeService.findAll();
        List<ProductPlanBatch> productPlanBatches = productTypes.stream()
                .map((productType) -> getOne(productType.getId(), date)).collect(Collectors.toList());
        return productPlanBatches;
    }

    private Integer countProductPlanAmount(Long productTypeId, LocalDate date) {
        List<ProductPlanOperation> productPlanOperations = productPlanOperationService.getAll(productTypeId, date, date);
        Integer amount = productPlanOperations.stream().mapToInt(ProductPlanOperation::getProductAmount).sum();
        return amount;
    }
}
