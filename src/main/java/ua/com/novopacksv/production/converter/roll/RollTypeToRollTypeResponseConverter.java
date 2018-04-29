package ua.com.novopacksv.production.converter.roll;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollTypeResponse;
import ua.com.novopacksv.production.model.rollModel.RollType;

@Component
public class RollTypeToRollTypeResponseConverter implements Converter<RollType, RollTypeResponse> {

    @Override
    public RollTypeResponse convert(RollType source) {
        RollTypeResponse result = new RollTypeResponse();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setThickness(source.getThickness());
        result.setMinWeight(source.getMinWeight());
        result.setMaxWeight(source.getMaxWeight());
        result.setColorCode(source.getColorCode());
        return result;
    }

}