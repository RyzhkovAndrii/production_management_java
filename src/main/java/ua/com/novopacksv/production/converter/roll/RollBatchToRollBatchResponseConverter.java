package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollBatchResponse;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;

@Component
public class RollBatchToRollBatchResponseConverter implements Converter<RollBatch, RollBatchResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public RollBatchResponse convert(RollBatch source) {
        RollManufactured sourceRollManufactured = source.getRollManufactured();
        String dateManufactured = conversionService
                .convert(sourceRollManufactured.getManufacturedDate(), String.class);
        RollBatchResponse result = new RollBatchResponse();
        result.setDateManufactured(dateManufactured);
        result.setRollTypeId(sourceRollManufactured.getRollType().getId());
        result.setManufacturedAmount(source.getManufacturedAmount());
        result.setUsedAmount(source.getUsedAmount());
        result.setLeftOverAmount(source.getLeftOverAmount());
        result.setReadyToUse(sourceRollManufactured.getReadyToUse());
        return result;
    }

}