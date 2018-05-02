package ua.com.novopacksv.production.converter.datetime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Nullable
    @Override
    public LocalDateTime convert(@Nullable String source) {
        return (source == null || source.isEmpty()) ? null : LocalDateTime.parse(source, formatter);
    }

}