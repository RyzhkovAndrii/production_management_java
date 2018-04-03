package ua.com.novopacksv.production.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public <T> List<T> convert(List<?> list, Class<T> type) {
        return list.stream()
                .map(element -> convert(element, type))
                .collect(Collectors.toList());
    }

}
