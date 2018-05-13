package ua.com.novopacksv.production.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum<?>> enumClass;

    public void initialize(EnumValue enumValue) {
        enumClass = enumValue.value();
    }

    public boolean isValid(String stringValue, ConstraintValidatorContext context) {
        return isValueInEnum(stringValue, enumClass);
    }

    private boolean isValueInEnum(String stringValue, Class<? extends Enum<?>> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch((enumValue) -> enumValue.name().equals(stringValue.toUpperCase()));
    }

}