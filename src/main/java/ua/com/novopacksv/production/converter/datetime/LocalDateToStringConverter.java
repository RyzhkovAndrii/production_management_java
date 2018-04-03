package ua.com.novopacksv.production.converter.datetime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Nullable
    @Override
    public String convert(@Nullable LocalDate localDate) {
        return localDate == null ? null : localDate.format(formatter);
    }

}