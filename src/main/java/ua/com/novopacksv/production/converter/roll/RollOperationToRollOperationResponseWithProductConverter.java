package ua.com.novopacksv.production.converter.roll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.roll.RollOperationResponseWithProduct;
import ua.com.novopacksv.production.model.rollModel.RollOperation;

@Component
public class RollOperationToRollOperationResponseWithProductConverter
        implements Converter<RollOperation, RollOperationResponseWithProduct> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public RollOperationResponseWithProduct convert(RollOperation source) {
        String operationDate = conversionService.convert(source.getOperationDate(), String.class);
        String manufacturedDate = conversionService
                .convert(source.getRollManufactured().getManufacturedDate(), String.class);
        RollOperationResponseWithProduct result = new RollOperationResponseWithProduct();
        result.setId(source.getId());
        result.setOperationDate(operationDate);
        result.setOperationType(source.getOperationType().name());
        result.setManufacturedDate(manufacturedDate);
        result.setRollTypeId(source.getRollManufactured().getRollType().getId());
        result.setRollAmount(source.getRollAmount());
        result.setProductType(source.getProductType());
        return result;
    }

}