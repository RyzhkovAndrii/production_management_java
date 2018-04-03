package ua.com.novopacksv.production.converter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.user.ModificationResponse;
import ua.com.novopacksv.production.model.userModel.Modification;

@Component
public class ModificationToModificationResponseConverter implements Converter<Modification, ModificationResponse> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationResponse convert(Modification source) {
        String modificationDate = conversionService.convert(source.getModificationDate(), String.class);
        ModificationResponse result = new ModificationResponse();
        result.setId(source.getId());
        result.setUserId(source.getUser().getId());
        result.setModificationDate(modificationDate);
        result.setTable(source.getTableType().name());
        return result;
    }

}