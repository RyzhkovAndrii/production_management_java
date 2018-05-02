package ua.com.novopacksv.production.converter.datetime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Nullable
    @Override
    public String convert(@Nullable LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(formatter);
    }

}