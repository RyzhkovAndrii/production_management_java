package ua.com.novopacksv.production.converter;

import java.util.List;

public interface ModelConversionService {

    <T> T convert(Object obj, Class<T> type);

    <T> List<T> convert(List<?> list, Class<T> type);

}
