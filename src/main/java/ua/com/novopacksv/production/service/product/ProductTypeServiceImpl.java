package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.productRepository.ProductTypeRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Autowired
    @Lazy
    private ProductLeftOverService productLeftOverService;

    @Autowired
    @Lazy
    private ProductCheckService productCheckService;

    @Override
    @Transactional(readOnly = true)
    public ProductType findById(Long id) throws ResourceNotFoundException {
        return productTypeRepository.findById(id).orElseThrow(() ->
        {
            String message = String.format("Product type with id = %d was not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductType save(ProductType productType) {
        Long existedId = checkIfTheSameType(productType);
        if (existedId != null) {
            return findById(existedId);
        } else {
            productTypeRepository.save(productType);
            productLeftOverService.saveByProductType(productType);
            productCheckService.createNewProductCheckAndSave(productType);
            return productType;
        }
    }

    @Override
    public ProductType update(ProductType productType) throws ResourceNotFoundException {
        findById(productType.getId());
        return productTypeRepository.save(productType);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        productTypeRepository.delete(findById(id));
    }

    private Long checkIfTheSameType(ProductType productType) {
        ProductType productType1 = productTypeRepository.findByNameAndWeightAndColorCode(productType.getName(),
                productType.getWeight(), productType.getColorCode());
        return productType1.getId();
    }
}