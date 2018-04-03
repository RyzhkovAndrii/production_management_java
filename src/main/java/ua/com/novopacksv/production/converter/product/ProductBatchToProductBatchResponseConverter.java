package ua.com.novopacksv.production.converter.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductBatchResponse;
import ua.com.novopacksv.production.model.productModel.ProductBatch;

@Component
public class ProductBatchToProductBatchResponseConverter implements Converter<ProductBatch, ProductBatchResponse> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public ProductBatchResponse convert(ProductBatch source) {
        String creationDate = conversionService.convert(source.getCreationDate(), String.class);
        ProductBatchResponse result = new ProductBatchResponse();
        result.setId(source.getId());
        result.setProductTypeId(source.getProductType().getId());
        result.setCreationDate(creationDate);
        result.setAmount(source.getAmount());
        return result;
    }

}