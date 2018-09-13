package ua.com.novopacksv.production.converter.plan;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.MachinePlanItemResponse;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;

@Component
public class MachinePlanItemToMachinePlanItemResponseConverter implements Converter<MachinePlanItem, MachinePlanItemResponse> {

    @Override
    public MachinePlanItemResponse convert(MachinePlanItem source) {
        MachinePlanItemResponse response = new MachinePlanItemResponse();
        response.setId(source.getId());
        response.setRollTypeId(source.getRollType().getId());
        response.setRollAmount(source.getRollAmount());
        response.setProductAmount(source.getProductAmount());
        response.setMachinePlanId(source.getMachinePlan().getId());
        return response;
    }

}
