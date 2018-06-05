package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * The class implements interface {@link ProductCheckService} and contains logic for work with product's checks
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductCheckServiceImpl implements ProductCheckService {

    /**
     * An object of repository layer for have access to methods of work with DB
     */
    private final ProductCheckRepository productCheckRepository;

    /**
     * An object of service layer for have access to methods of work with product type
     */
    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    /**
     * Method finds one check by product type's id
     *
     * @param productTypeId - product type's id
     * @return product's check
     * @throws ResourceNotFoundException if product's check doesn't exist
     */
    @Override
    @Transactional(readOnly = true)
    public ProductCheck findOneByProductTypeId(Long productTypeId) throws ResourceNotFoundException {
        ProductCheck productCheck = productCheckRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
            String message = String.format("Product check with product type id = %d is not found!", productTypeId);
            log.error("Method findOneByProductTypeId(Long productTypeId): product's check was not found for " +
                    "product type's id = {}", productTypeId);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findOneByProductTypeId(Long productTypeId): product's check {} was found for product type's " +
                "id = {}", productCheck, productTypeId);
        return productCheck;
    }

    /**
     * Method finds all products' checks
     *
     * @return list of products' checks
     */
    @Override
    public List<ProductCheck> findAll() {
        log.debug("Method findAll(): list of products' checks are finding");
        return productCheckRepository.findAll();
    }

    /**
     * Method save updated product's check
     *
     * @param productCheck - updated product's check
     * @return saved updated product's check
     * @throws ResourceNotFoundException if there is not product type for this check
     */
    @Override
    public ProductCheck update(ProductCheck productCheck) throws ResourceNotFoundException {
        ProductType productType = productTypeService.findById(productCheck.getId());
        productCheck.setProductType(productType);
        log.debug("Method update(ProductCheck productCheck): product's check {} was saved", productCheck);
        return productCheckRepository.save(productCheck);
    }

    /**
     * Method creates and saves a new check for new product type
     *
     * @param productType - new product type
     */
    @Override
    public void createNewProductCheckAndSave(ProductType productType) {
        ProductCheck productCheck = new ProductCheck();
        productCheck.setProductType(productType);
        productCheck.setProductLeftOverCheckStatus(CheckStatus.NOT_CHECKED);
        productCheckRepository.save(productCheck);
        log.debug("Method createNewProductCheckAndSave(ProductType productType): new check {} was created and saved for" +
                "product type {}", productCheck, productType);
    }

    /**
     * Method starts method setNotCheckedStatus() for all checks. This method are run automatically
     */
    @Override
    public void setNotCheckedStatusForAll() {
        findAll().forEach(this::setNotCheckedStatus);
        log.debug("Method setNotCheckedStatusForAll(): for all checks was set status NOT_CHECKED");
    }

    /**
     * Method changes status to NOT_CHECKED for one product's check
     *
     * @param productCheck - product's check
     */
    private void setNotCheckedStatus(ProductCheck productCheck) {
        productCheck.setProductLeftOverCheckStatus(CheckStatus.NOT_CHECKED);
        productCheckRepository.save(productCheck);
        log.debug("Method setNotCheckedStatus(ProductCheck productCheck): for product's check {} was set status {}",
                productCheck, productCheck.getProductLeftOverCheckStatus());
    }
}
