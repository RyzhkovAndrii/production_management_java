package ua.com.novopacksv.production.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private EntityManager entityManager;

    private Class<?> clazz;

    private String columnName;

    public void initialize(Unique unique) {
        this.clazz = unique.value();
        this.columnName = unique.column();
    }

    public boolean isValid(Object columnValue, ConstraintValidatorContext context) {
        String query = String.format("from %s where %s = :columnValue", clazz.getName(), columnName);
        return entityManager.createQuery(query).setParameter("columnValue", columnValue).getResultList().isEmpty();
    }

}