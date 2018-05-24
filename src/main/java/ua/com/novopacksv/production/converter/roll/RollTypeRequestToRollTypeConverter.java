package ua.com.novopacksv.production.converter.roll;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollTypeRequest;
import ua.com.novopacksv.production.model.rollModel.RollType;

@Component
public class RollTypeRequestToRollTypeConverter implements Converter<RollTypeRequest, RollType> {

    @Override
    public RollType convert(RollTypeRequest source) {
        RollType result = new RollType();
        result.setNote(source.getNote());
        result.setThickness(source.getThickness());
        result.setMinWeight(source.getMinWeight());
        result.setMaxWeight(source.getMaxWeight());
        result.setLength(source.getLength());
        result.setColorCode(source.getColorCode());
        return result;
    }

}