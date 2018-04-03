package ua.com.novopacksv.production.converter.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.user.ModificationResponse;
import ua.com.novopacksv.production.model.userModel.Modification;

import java.time.format.DateTimeFormatter;

@Component
public class ModificationToModificationResponseConverter implements Converter<Modification, ModificationResponse> {

    @Override
    public ModificationResponse convert(Modification source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ModificationResponse result = new ModificationResponse();
        result.setUserId(source.getUser().getId());
        result.setModificationDate(source.getModificationDate().format(formatter));
        result.setTableTypeName(source.getTableType().name());
        return result;
    }

}