package ua.com.novopacksv.production.converter.plan;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.plan.RollPlanOperationResponse;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;

@Component
public class RollPlanOperationToRollPlanOperationResponseConverter implements Converter<RollPlanOperation, RollPlanOperationResponse> {

    @Override
    public RollPlanOperationResponse convert(RollPlanOperation source) {
        RollPlanOperationResponse response = new RollPlanOperationResponse();
        response.setDate(source.getDate());
        response.setRollTypeId(source.getRollType().getId());
        response.setRollAmount(source.getRollQuantity());
        return response;
    }
}
