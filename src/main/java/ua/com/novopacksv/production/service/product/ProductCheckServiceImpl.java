package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductCheck;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.CheckStatus;
import ua.com.novopacksv.production.repository.productRepository.ProductCheckRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCheckServiceImpl implements ProductCheckService {

    private final ProductCheckRepository productCheckRepository;

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Override
    @Transactional(readOnly = true)
    public ProductCheck findOneByProductTypeId(Long productTypeId) {
        return productCheckRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
            String message = String.format("Product check with product type id = %d is not found!", productTypeId);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public List<ProductCheck> findAll() {
        return productCheckRepository.findAll();
    }

    @Override
    public ProductCheck update(ProductCheck productCheck) {
        ProductType productType = productTypeService.findById(productCheck.getId());
        productCheck.setProductType(productType);
        return productCheckRepository.save(productCheck);
    }

    @Override
    public void createNewProductCheckAndSave(ProductType productType) {
        ProductCheck productCheck = new ProductCheck();
        productCheck.setProductType(productType);
        productCheck.setProductLeftOverCheckStatus(CheckStatus.NOT_CHECKED);
        productCheckRepository.save(productCheck);
    }

    @Override
    public void setNotCheckedStatusForAll() {
        findAll().forEach(this::setNotCheckedStatus);
    }

    private void setNotCheckedStatus(ProductCheck productCheck){
        productCheck.setProductLeftOverCheckStatus(CheckStatus.NOT_CHECKED);
        productCheckRepository.save(productCheck);
    }
}
