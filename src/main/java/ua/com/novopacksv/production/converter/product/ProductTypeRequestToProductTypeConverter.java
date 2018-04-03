package ua.com.novopacksv.production.converter.product;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductTypeRequest;
import ua.com.novopacksv.production.model.productModel.ProductType;

@Component
public class ProductTypeRequestToProductTypeConverter implements Converter<ProductTypeRequest, ProductType> {

    @Override
    public ProductType convert(ProductTypeRequest source) {
        ProductType result = new ProductType();
        result.setName(source.getName());
        result.setWeight(source.getWeight());
        result.setColorCode(source.getColorCode());
        return result;
    }

}
