package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.ProductPlanOperationRequest;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.service.plan.MachinePlanService;
import ua.com.novopacksv.production.service.product.ProductTypeService;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.time.LocalDate;

@Component
public class ProductPlanOperationRequestToProductPlanOperationConverter implements Converter<ProductPlanOperationRequest,
        ProductPlanOperation> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    @Autowired
    @Lazy
    private MachinePlanService machinePlanService;

    @Override
    public ProductPlanOperation convert(ProductPlanOperationRequest source) {
        ProductPlanOperation result = new ProductPlanOperation();
        LocalDate date = conversionService.convert(source.getDate(), LocalDate.class);
        result.setDate(date);
        result.setProductType(productTypeService.findById(source.getProductTypeId()));
        result.setRollType(rollTypeService.findById(source.getRollTypeId()));
        result.setProductAmount(source.getProductAmount());
        result.setRollAmount(source.getRollAmount());
        if (source.getMachinePlanId() != null){
            result.setMachinePlan(machinePlanService.findById(source.getMachinePlanId()));
        }
        return result;
    }
}
