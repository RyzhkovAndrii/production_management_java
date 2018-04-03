package ua.com.novopacksv.production.converter.product;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductTypeResponse;
import ua.com.novopacksv.production.model.productModel.ProductType;

@Component
public class ProductTypeToProductTypeResponseConverter implements Converter<ProductType, ProductTypeResponse> {

    @Override
    public ProductTypeResponse convert(ProductType source) {
        ProductTypeResponse result = new ProductTypeResponse();
        result.setName(source.getName());
        result.setWeight(source.getWeight());
        result.setColorCode(source.getColorCode());
        return result;
    }

}