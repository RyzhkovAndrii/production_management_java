package ua.com.novopacksv.production.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeValidator implements ConstraintValidator<LocalDateTimeFormat, String> {

    @Autowired
    private ConversionService conversionService;

    public void initialize(LocalDateTimeFormat localDateTimeFormat){
    }

    public boolean isValid(String date, ConstraintValidatorContext context){
        try {
            conversionService.convert(date, LocalDateTime.class);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
