package ua.com.novopacksv.production.converter.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductBatchRequest;
import ua.com.novopacksv.production.model.productModel.ProductBatch;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ProductBatchRequestToProductBatchConverter implements Converter<ProductBatchRequest, ProductBatch> {

    @Autowired
    private ProductTypeService productTypeService;

    @Override
    public ProductBatch convert(ProductBatchRequest source) {
        ProductBatch result = new ProductBatch();
        ProductType productType = productTypeService.findById(source.getProductTypeId());
        result.setProductType(productType);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate creationDate = LocalDate.parse(source.getCreationDate(), formatter);
        result.setCreationDate(creationDate);
        result.setAmount(source.getAmount());
        return result;
    }

}
