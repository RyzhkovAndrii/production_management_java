package ua.com.novopacksv.production.service.product;

import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.util.List;

public interface ProductLeftOverService extends BaseEntityService<ProductLeftOver> {

    List<ProductLeftOver> findOnDate(LocalDate date);

    ProductLeftOver findByProductType_IdOnDate(Long productTypeId, LocalDate date);
}
