package ua.com.novopacksv.production.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureOrPresentValidatorForStringDate.class)
public @interface FutureOrPresent {

    String message() default "date can not be in past!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}