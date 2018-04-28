package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
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
        return (existedId != null) ? findById(existedId) : productTypeRepository.save(productType);
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
        Long existedId = null;
        List<ProductType> productTypes = findAll();
        for (ProductType type : productTypes) {
            if (productType.getName().equals(type.getName()) & productType.getColorCode().equals(type.getColorCode())
                    & productType.getWeight().equals(type.getWeight())) {
                existedId = type.getId();
            }
        }
        return existedId;
    }
}