package ua.com.novopacksv.production.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;

import java.util.List;

@Service
@Transactional
public class ProductLeftOverServiceImpl implements ProductLeftOverService {

    @Override
    public ProductLeftOver findById(Long id) {
        return null;
    }

    @Override
    public List<ProductLeftOver> findAll() {
        return null;
    }

    @Override
    public ProductLeftOver save(ProductLeftOver productLeftOver) {
        return null;
    }

    @Override
    public ProductLeftOver update(ProductLeftOver productLeftOver) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}