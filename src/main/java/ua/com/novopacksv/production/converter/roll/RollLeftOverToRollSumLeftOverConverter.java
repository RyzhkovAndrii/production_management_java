package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollSumLeftOverResponse;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;

@Component
public class RollLeftOverToRollSumLeftOverConverter implements Converter<RollLeftOver, RollSumLeftOverResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public RollSumLeftOverResponse convert(RollLeftOver source) {
        String date = conversionService.convert(source.getDate(), String.class);
        RollSumLeftOverResponse result = new RollSumLeftOverResponse();
        result.setDate(date);
        result.setTotal(source.getAmount());
        return result;
    }

}