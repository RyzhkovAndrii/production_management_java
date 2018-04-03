package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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

    @Autowired
    private ConversionService conversionService;

    @Override
    public RollBatch convert(RollBatchRequest source) {
        RollType rollType = rollTypeService.findById(source.getRollTypeId());
        LocalDate creationDate = conversionService.convert(source.getCreationDate(), LocalDate.class);
        RollBatch result = new RollBatch();
        result.setRollType(rollType);
        result.setCreationDate(creationDate);
        result.setAmount(source.getAmount());
        return result;
    }

}