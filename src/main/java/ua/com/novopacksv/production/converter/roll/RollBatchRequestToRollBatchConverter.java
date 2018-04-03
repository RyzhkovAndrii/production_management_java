package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollBatchRequest;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RollBatchRequestToRollBatchConverter implements Converter<RollBatchRequest, RollBatch> {

    @Autowired
    private RollTypeService rollTypeService;

    @Override
    public RollBatch convert(RollBatchRequest source) {
        RollBatch result = new RollBatch();
        RollType rollType = rollTypeService.findById(source.getRollTypeId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate creationDate = LocalDate.parse(source.getCreationDate(), formatter);
        result.setRollType(rollType);
        result.setCreationDate(creationDate);
        result.setAmount(source.getAmount());
        return result;
    }

}