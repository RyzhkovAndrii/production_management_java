package ua.com.novopacksv.production.converter.roll;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollBatchResponse;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

import java.time.format.DateTimeFormatter;

@Component
public class RollBatchToRollBatchResponseConverter implements Converter<RollBatch, RollBatchResponse> {

    @Override
    public RollBatchResponse convert(RollBatch source) {
        RollBatchResponse result = new RollBatchResponse();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        result.setRollTypeId(source.getRollType().getId());
        result.setCreationDate(source.getCreationDate().format(formatter));
        result.setReadyToUseDate(source.getReadyToUseDate().format(formatter));
        result.setAmount(source.getAmount());
        return result;
    }

}