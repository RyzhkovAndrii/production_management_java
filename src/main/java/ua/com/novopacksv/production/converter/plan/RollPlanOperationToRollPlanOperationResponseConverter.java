package ua.com.novopacksv.production.converter.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.RollPlanOperationResponse;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;

@Component
public class RollPlanOperationToRollPlanOperationResponseConverter implements Converter<RollPlanOperation, RollPlanOperationResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public RollPlanOperationResponse convert(RollPlanOperation source) {
        RollPlanOperationResponse response = new RollPlanOperationResponse();
        String date = conversionService.convert(source.getDate(), String.class);
        response.setId(source.getId());
        response.setDate(date);
        response.setRollTypeId(source.getRollType().getId());
        response.setRollAmount(source.getRollQuantity());
        return response;
    }
}
