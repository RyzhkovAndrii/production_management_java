package ua.com.novopacksv.production.converter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.user.TableModificationResponse;
import ua.com.novopacksv.production.model.userModel.TableModification;

@Component
public class TableModificationToTableModificationResponseConverter implements Converter<TableModification, TableModificationResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public TableModificationResponse convert(TableModification source) {
        String modificationDate = conversionService.convert(source.getModificationDateTime(), String.class);
        TableModificationResponse result = new TableModificationResponse();
        result.setId(source.getId());
        result.setUserId(source.getUser().getId());
        result.setModificationDateTime(modificationDate);
        result.setTableType(source.getTableType().name());
        return result;
    }

}