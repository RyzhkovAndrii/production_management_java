package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollOperationResponse;
import ua.com.novopacksv.production.model.rollModel.RollOperation;

@Component
public class RollOperationToRollOperationResponseConverter implements Converter<RollOperation, RollOperationResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public RollOperationResponse convert(RollOperation source) {
        String operationDate = conversionService.convert(source.getOperationDate(), String.class);
        String manufacturedDate = conversionService
                .convert(source.getRollManufactured().getManufacturedDate(), String.class);
        RollOperationResponse result = new RollOperationResponse();
        result.setId(source.getId());
        result.setOperationDate(operationDate);
        result.setOperationType(source.getOperationType().name());
        result.setManufacturedDate(manufacturedDate);
        result.setRollTypeId(source.getRollManufactured().getRollType().getId());
        result.setRollAmount(source.getRollAmount());
        if(source.getProductType().getId() != null){
            result.setProductTypeIdForUseOperation(source.getProductType().getId());
        }
        return result;
    }

}