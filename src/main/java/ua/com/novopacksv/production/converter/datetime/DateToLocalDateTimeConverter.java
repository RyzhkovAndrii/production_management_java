package ua.com.novopacksv.production.converter.datetime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime convert(@Nullable Date date) {
        return date == null
                ? null
                : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}