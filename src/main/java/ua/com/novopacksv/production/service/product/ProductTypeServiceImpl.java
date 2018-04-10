package ua.com.novopacksv.production.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.util.List;

@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService {

    @Override
    public ProductType findById(Long id) {
        return null;
    }

    @Override
    public List<ProductType> findAll() {
        return null;
    }

    @Override
    public ProductType save(ProductType productType) {
        return null;
    }

    @Override
    public ProductType update(ProductType productType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}