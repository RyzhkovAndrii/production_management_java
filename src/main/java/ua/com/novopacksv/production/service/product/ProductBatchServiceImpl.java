package ua.com.novopacksv.production.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.productModel.ProductBatch;

import java.util.List;

@Service
@Transactional
public class ProductBatchServiceImpl implements ProductBatchService {

    @Override
    public ProductBatch findById(Long id) {
        return null;
    }

    @Override
    public List<ProductBatch> findAll() {
        return null;
    }

    @Override
    public ProductBatch save(ProductBatch productBatch) {
        return null;
    }

    @Override
    public ProductBatch update(ProductBatch productBatch) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}