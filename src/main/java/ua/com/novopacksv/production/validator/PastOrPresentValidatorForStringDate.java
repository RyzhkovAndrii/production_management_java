package ua.com.novopacksv.production.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PastOrPresentValidatorForStringDate implements ConstraintValidator<PastOrPresent, String> {

    @Autowired
    private ConversionService conversionService;

    public void initialize(PastOrPresent pastOrPresent) {
    }

    public boolean isValid(String stringDate, ConstraintValidatorContext context) {
        try {
            assert null != stringDate;
            LocalDate date = conversionService.convert(stringDate, LocalDate.class);
            return isPastOrPresent(date);
        } catch (Exception ex) {
            return true;
        }
    }

    private boolean isPastOrPresent(LocalDate date) {
        LocalDate today = LocalDate.now();
        return date.isBefore(today) || date.isEqual(today);
    }

}