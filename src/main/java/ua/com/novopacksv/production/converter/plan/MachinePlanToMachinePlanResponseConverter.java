package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.MachinePlanResponse;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.service.plan.MachinePlanService;

import java.math.BigDecimal;

@Component
public class MachinePlanToMachinePlanResponseConverter implements Converter<MachinePlan, MachinePlanResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Autowired
    @Lazy
    private MachinePlanService machinePlanService;

    @Override
    public MachinePlanResponse convert(MachinePlan source) {
        MachinePlanResponse response = new MachinePlanResponse();
        response.setId(source.getId());
        response.setMachineNumber(source.getMachineNumber());
        String timeStart = conversionService.convert(source.getTimeStart(), String.class);
        response.setTimeStart(timeStart);
        response.setProductTypeId(source.getProductType().getId());
        response.setProductAmount(source.getProductAmount());
        String duration = String.valueOf(machinePlanService.getDuration(source));
        response.setDuration(duration);
        return response;
    }
}
