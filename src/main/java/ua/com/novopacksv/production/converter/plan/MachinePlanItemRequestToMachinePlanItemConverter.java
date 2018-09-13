package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.MachinePlanItemRequest;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.roll.RollTypeService;

@Component
public class MachinePlanItemRequestToMachinePlanItemConverter implements Converter<MachinePlanItemRequest, MachinePlanItem> {

    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    @Override
    public MachinePlanItem convert(MachinePlanItemRequest source) {
        RollType rollType = rollTypeService.findById(source.getRollTypeId());
        MachinePlanItem result = new MachinePlanItem();
        result.setRollType(rollType);
        result.setProductAmount(source.getProductAmount());
        result.setRollAmount(source.getRollAmount());
        return result;
    }
}
