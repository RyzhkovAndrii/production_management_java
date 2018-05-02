package ua.com.novopacksv.production.converter.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductOperationRequest;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDate;

@Component
public class ProductOperationRequestToProductOperationConverter implements
        Converter<ProductOperationRequest, ProductOperation> {

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public ProductOperation convert(ProductOperationRequest source) {
        ProductOperation result = new ProductOperation();
        ProductType productType = productTypeService.findById(source.getProductTypeId());
        LocalDate date = conversionService.convert(source.getOperationDate(), LocalDate.class);
        result.setProductType(productType);
        result.setDate(date);
        result.setProductOperationType(ProductOperationType.valueOf(source.getOperationType()));
        result.setAmount(source.getAmount());
        return result;
    }
}
