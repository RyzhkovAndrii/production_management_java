package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.ProductPlanOperationResponse;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;

@Component
public class ProductPlanOperationToProductPlanOperationResponseConverter implements Converter<ProductPlanOperation,
        ProductPlanOperationResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public ProductPlanOperationResponse convert(ProductPlanOperation source) {
        ProductPlanOperationResponse result = new ProductPlanOperationResponse();
        result.setId(source.getId());
        String date = conversionService.convert(source.getDate(), String.class);
        result.setDate(date);
        result.setProductTypeId(source.getProductType().getId());
        result.setRollTypeId(source.getRollType().getId());
        result.setProductAmount(source.getProductAmount());
        result.setRollAmount(source.getRollAmount());
        if (source.getMachinePlan() != null) {
            result.setMachinePlanId(source.getMachinePlan().getId());
        }
        return result;
    }
}
