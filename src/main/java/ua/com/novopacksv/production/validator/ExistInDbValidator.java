package ua.com.novopacksv.production.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistInDbValidator implements ConstraintValidator<ExistInDb, Long> {

    @Autowired
    private EntityManager entityManager;

    private Class<?> clazz;

    @Override
    public void initialize(ExistInDb existInDb) {
        this.clazz = existInDb.value();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return id == null || entityManager.find(clazz, id) != null;
    }

}