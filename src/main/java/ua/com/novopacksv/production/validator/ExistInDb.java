package ua.com.novopacksv.production.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistInDbValidator.class)
public @interface ExistInDb {

    Class<?> value();

    String message() default "entity is not found!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}