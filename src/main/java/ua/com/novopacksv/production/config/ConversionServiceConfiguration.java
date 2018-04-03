package ua.com.novopacksv.production.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConversionServiceConfiguration {

    @Bean
    @Autowired
    public ConversionServiceFactoryBean conversionService(Set<Converter> converters) {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        factory.setConverters(converters);
        return factory;
    }

}
