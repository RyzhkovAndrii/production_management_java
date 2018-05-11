package ua.com.novopacksv.production.service.product;

import ua.com.novopacksv.production.model.productModel.ProductCheck;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.util.List;

public interface ProductCheckService {

    ProductCheck findOneByProductTypeId(Long productTypeId);

    List<ProductCheck> findAll();

    ProductCheck update(ProductCheck productCheck);

    void createNewProductCheckAndSave(ProductType productType);

    void setNotCheckedStatusForAll();
}
