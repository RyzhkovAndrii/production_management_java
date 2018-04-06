package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollBatchResponse;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

@Component
public class RollBatchToRollBatchResponseConverter implements Converter<RollBatch, RollBatchResponse> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public RollBatchResponse convert(RollBatch source) {
        String dateManufactured = conversionService
                .convert(source.getRollManufactured().getManufacturedDate(), String.class);
        RollBatchResponse result = new RollBatchResponse();
        result.setDateManufactured(dateManufactured);
        result.setRollTypeId(source.getRollManufactured().getRollType().getId());
        result.setManufacturedAmount(source.getManufactureAmount());
        result.setUsedAmount(source.getUsedAmount());
        result.setLeftOverAmount(source.getLeftOverAmount());
        return result;
    }

}