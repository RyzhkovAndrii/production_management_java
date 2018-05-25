package ua.com.novopacksv.production.converter.norm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.norm.NormRequest;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.product.ProductTypeService;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NormRequestToNormConverter implements Converter<NormRequest, Norm> {

    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Override
    public Norm convert(NormRequest source) {
        Norm norm = new Norm();
        norm.setNorm(source.getNorm());
        List<RollType> rollTypes =
                source.getRollTypeIds().stream().map((id) -> rollTypeService.findById(id)).collect(Collectors.toList());
        norm.setRollTypes(rollTypes);
        norm.setProductType(productTypeService.findById(source.getProductTypeId()));
        return norm;
    }
}
