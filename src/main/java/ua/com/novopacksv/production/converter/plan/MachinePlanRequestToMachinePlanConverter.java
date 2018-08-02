package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.MachinePlanRequest;
import ua.com.novopacksv.production.exception.LocalDateTimeFormatException;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDateTime;

@Component
public class MachinePlanRequestToMachinePlanConverter implements Converter<MachinePlanRequest, MachinePlan> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Override
    public MachinePlan convert(MachinePlanRequest source) throws LocalDateTimeFormatException{
        MachinePlan result = new MachinePlan();
        result.setMachineNumber(source.getMachineNumber());
        try {
            LocalDateTime timeStart = conversionService.convert(source.getTimeStart(), LocalDateTime.class);
            result.setTimeStart(timeStart);
        } catch (Exception e) {
            throw new LocalDateTimeFormatException("Date format is incorrect!");
        }
        result.setProductType(productTypeService.findById(source.getProductTypeId()));
        result.setProductAmount(source.getProductAmount());
        return result;
    }
}
