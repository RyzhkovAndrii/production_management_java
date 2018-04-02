package ua.com.novopacksv.production.converter;

import java.util.Collection;

public interface ModelConversionService {

    <T> T convert(Object obj, Class<T> type);

    <T> Collection<T> convert(Collection<?> collection, Class<T> type);

}
