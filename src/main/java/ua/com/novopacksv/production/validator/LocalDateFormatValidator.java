package ua.com.novopacksv.production.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class LocalDateFormatValidator implements ConstraintValidator<LocalDateFormat, String> {

    @Autowired
    private ConversionService conversionService;

    public void initialize(LocalDateFormat localDateFormat) {
    }

    public boolean isValid(String date, ConstraintValidatorContext context) {
        try {
            conversionService.convert(date, LocalDate.class);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}