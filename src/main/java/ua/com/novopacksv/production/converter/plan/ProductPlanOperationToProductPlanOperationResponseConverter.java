package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.ProductPlanOperationResponse;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.plan.ProductPlanOperationService;

import java.time.LocalDate;

@Component
public class ProductPlanOperationToProductPlanOperationResponseConverter implements Converter<ProductPlanOperation,
        ProductPlanOperationResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Autowired
    @Lazy
    private ProductPlanOperationService operationService;

    @Override
    public ProductPlanOperationResponse convert(ProductPlanOperation source) {
        ProductPlanOperationResponse result = new ProductPlanOperationResponse();
        ProductType productType = source.getProductType();
        RollType rollType = source.getRollType();
        LocalDate date = source.getDate();
        Integer rollToMachinePlanAmount = operationService.getRollToMachinePlanAmount(productType, rollType, date);
        String stringDate = conversionService.convert(date, String.class);
        result.setId(source.getId());
        result.setDate(stringDate);
        result.setProductTypeId(productType.getId());
        result.setRollTypeId(rollType.getId());
        result.setProductAmount(source.getProductAmount());
        result.setRollAmount(source.getRollAmount());
        result.setRollToMachinePlane(rollToMachinePlanAmount);
        return result;
    }
}
