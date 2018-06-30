package ua.com.novopacksv.production.converter.norm;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.norm.NormForRollResponse;
import ua.com.novopacksv.production.model.normModel.Norm;

@Component
public class NormToNormForRollResponseConverter implements Converter<Norm, NormForRollResponse> {

    @Override
    public NormForRollResponse convert(Norm source) {
        NormForRollResponse response = new NormForRollResponse();
        response.setProductTypeId(source.getProductType().getId());
        response.setRollTypes(source.getRollTypes());
        response.setNorm(source.getNorm());
        return response;
    }
}
