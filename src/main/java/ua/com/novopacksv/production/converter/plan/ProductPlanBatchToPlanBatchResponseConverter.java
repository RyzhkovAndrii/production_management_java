package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.ProductPlanBatchResponse;
import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;
import ua.com.novopacksv.production.service.plan.MachinePlanService;

@Component
public class ProductPlanBatchToPlanBatchResponseConverter implements Converter<ProductPlanBatch,
        ProductPlanBatchResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Autowired
    @Lazy
    private MachinePlanService machinePlanService;

    @Override
    public ProductPlanBatchResponse convert(ProductPlanBatch source) {
        ProductPlanBatchResponse result = new ProductPlanBatchResponse();
        String date = conversionService.convert(source.getDate(), String.class);
        result.setDate(date);
        result.setManufacturedAmount(source.getManufacturedAmount());
        result.setSoldAmount(source.getUsedAmount());
        result.setProductTypeId(source.getProductType().getId());
        result.setProductToMachinePlane(machinePlanService
                .countProductAmountForMachinePlan(source.getProductType().getId(), source.getDate()));
        return result;
    }
}
