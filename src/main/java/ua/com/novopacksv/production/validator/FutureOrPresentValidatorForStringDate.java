package ua.com.novopacksv.production.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class FutureOrPresentValidatorForStringDate implements ConstraintValidator<FutureOrPresent, String> {

    @Autowired
    private ConversionService conversionService;

    public void initialize(FutureOrPresent constraint) {
    }

    public boolean isValid(String stringDate, ConstraintValidatorContext context) {
        try {
            assert null != stringDate;
            LocalDate date = conversionService.convert(stringDate, LocalDate.class);
            return isFutureOrPresent(date);
        } catch (Exception ex) {
            return true;
        }
    }

    private boolean isFutureOrPresent(LocalDate date) {
        LocalDate today = LocalDate.now();
        return date.isAfter(today) || date.isEqual(today);
    }

}