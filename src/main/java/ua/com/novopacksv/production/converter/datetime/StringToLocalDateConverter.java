package ua.com.novopacksv.production.converter.datetime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Nullable
    @Override
    public LocalDate convert(@Nullable String source) {
        return (source == null || source.isEmpty()) ? null : LocalDate.parse(source, formatter);
    }

}
