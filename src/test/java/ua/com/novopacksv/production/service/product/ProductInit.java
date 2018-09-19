package ua.com.novopacksv.production.service.product;

import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.time.LocalDate;

public class ProductInit {

    public ProductType productTypeInit() {
        return new ProductType("T-4-45", 8.0, "#ffffff");
    }

    public ProductType productTypeInit2() {
        return new ProductType("HP-1", 12.0, "#ffffff");
    }

    public ProductOperation productOperationInit(ProductType productType) {
        return new ProductOperation(LocalDate.now(), productType, ProductOperationType.MANUFACTURED, 500);
    }
}
