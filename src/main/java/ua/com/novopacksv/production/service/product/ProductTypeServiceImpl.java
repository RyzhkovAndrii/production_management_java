package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.productRepository.ProductTypeRepository;

import java.util.List;

/**
 *The class implements interface {@link ProductTypeService}
 * contains logic for work with {@link ProductTypeRepository}
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {

    /**
     * An object of repository layer for have access to methods of work with DB
     */
    private final ProductTypeRepository productTypeRepository;

    /**
     * An object of service layer for have access to methods of work with products' leftovers
     */
    @Autowired
    @Lazy
    private ProductLeftOverService productLeftOverService;

    /**
     * An object of service layer for have access to methods of work with products' checks
     */
    @Autowired
    @Lazy
    private ProductCheckService productCheckService;

    /**
     * Method finds product type by it's id
     * @param id - product's id
     * @return product type with pointed id
     * @throws ResourceNotFoundException if there is not product type with this id
     */
    @Override
    @Transactional(readOnly = true)
    public ProductType findById(Long id) throws ResourceNotFoundException {
        return productTypeRepository.findById(id).orElseThrow(() ->
        {
            String message = String.format("Product type with id = %d was not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    /**
     * Method finds all existed in db product types
     * @return list of product types
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    /**
     * Method finds all product types with the same product type's name
     * @param productTypeName - name of searching product type
     * @return list of product types with pointed name
     */
    @Override
    public List<ProductType> findAll(String productTypeName) {
        return productTypeRepository.findAllByName(productTypeName);
    }

    /**
     * Method saves a product type, creates and save product check and leftover for this type
     * @param productType - product type for save
     * @return saved product type
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductType save(ProductType productType) {
        productTypeRepository.save(productType);
        productLeftOverService.saveByProductType(productType);
        productCheckService.createNewProductCheckAndSave(productType);
        return productType;
    }

    /**
     * Method tests if product type exists and saves changed product type
     * @param productType - changed product type
     * @return changed product type
     * @throws ResourceNotFoundException if there is not a product type in db
     */
    @Override
    public ProductType update(ProductType productType) throws ResourceNotFoundException {
        findById(productType.getId());
        return productTypeRepository.save(productType);
    }

    /**
     * Method tests if product type exists and delete product type by id
     * @param id - product type's id
     * @throws ResourceNotFoundException if thete is not product type with id in db
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        productTypeRepository.delete(findById(id));
    }

}