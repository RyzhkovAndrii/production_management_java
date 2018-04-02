package ua.com.novopacksv.production.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModelConversionServiceSpringImpl implements ModelConversionService {

    private final ConversionService conversionService;

    @Override
    public <T> T convert(Object obj, Class<T> type) {
        return conversionService.convert(obj, type);
    }

    @Override
    public <T> Collection<T> convert(Collection<?> collection, Class<T> type) {
        return collection.stream()
                .map(element -> convert(element, type))
                .collect(Collectors.toList());
    }

}
