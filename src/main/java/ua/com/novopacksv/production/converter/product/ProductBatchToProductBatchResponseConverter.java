package ua.com.novopacksv.production.converter.product;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductBatchResponse;
import ua.com.novopacksv.production.model.productModel.ProductBatch;

import java.time.format.DateTimeFormatter;

@Component
public class ProductBatchToProductBatchResponseConverter implements Converter<ProductBatch, ProductBatchResponse> {

    @Override
    public ProductBatchResponse convert(ProductBatch source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ProductBatchResponse result = new ProductBatchResponse();
        result.setId(source.getId());
        result.setProductTypeId(source.getProductType().getId());
        result.setCreationDate(source.getCreationDate().format(formatter));
        result.setAmount(source.getAmount());
        return result;
    }

}