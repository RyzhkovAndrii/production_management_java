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
        String creationDate = conversionService.convert(source.getCreationDate(), String.class);
        String readyToUseDate = conversionService.convert(source.getReadyToUseDate(), String.class);
        RollBatchResponse result = new RollBatchResponse();
        result.setId(source.getId());
        result.setRollTypeId(source.getRollType().getId());
        result.setCreationDate(creationDate);
        result.setReadyToUseDate(readyToUseDate);
        result.setAmount(source.getAmount());
        return result;
    }

}