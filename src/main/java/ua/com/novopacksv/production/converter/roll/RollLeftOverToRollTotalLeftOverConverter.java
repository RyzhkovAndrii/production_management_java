package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollTotalLeftOverResponse;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;

@Component
public class RollLeftOverToRollTotalLeftOverConverter implements Converter<RollLeftOver, RollTotalLeftOverResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public RollTotalLeftOverResponse convert(RollLeftOver source) {
        String date = conversionService.convert(source.getDate(), String.class);
        RollTotalLeftOverResponse result = new RollTotalLeftOverResponse();
        result.setDate(date);
        result.setTotal(source.getAmount());
        return result;
    }

}