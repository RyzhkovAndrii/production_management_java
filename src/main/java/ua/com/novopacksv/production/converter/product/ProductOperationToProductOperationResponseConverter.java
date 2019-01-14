package ua.com.novopacksv.production.converter.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductOperationResponse;
import ua.com.novopacksv.production.model.productModel.ProductOperation;

@Component
public class ProductOperationToProductOperationResponseConverter implements
        Converter<ProductOperation, ProductOperationResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public ProductOperationResponse convert(ProductOperation source) {
        ProductOperationResponse result = new ProductOperationResponse();
        result.setId(source.getId());
        String date = conversionService.convert(source.getDate(), String.class);
        result.setOperationDate(date);
        result.setProductTypeId(source.getProductType().getId());
        result.setAmount(source.getAmount());
        result.setOperationType(String.valueOf(source.getProductOperationType()));
        return result;
    }
}
