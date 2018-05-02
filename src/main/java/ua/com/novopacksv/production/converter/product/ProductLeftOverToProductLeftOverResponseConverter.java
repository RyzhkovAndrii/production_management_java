package ua.com.novopacksv.production.converter.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductLeftOverResponse;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;

@Component
public class ProductLeftOverToProductLeftOverResponseConverter implements Converter<ProductLeftOver, ProductLeftOverResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public ProductLeftOverResponse convert(ProductLeftOver source) {
        ProductLeftOverResponse result = new ProductLeftOverResponse();
        String date = conversionService.convert(source.getLeftDate(), String.class);
        result.setProductTypeId(source.getProductType().getId());
        result.setAmount(source.getAmount());
        result.setDate(date);
        return result;
    }
}

