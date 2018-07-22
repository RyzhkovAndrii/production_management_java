package ua.com.novopacksv.production.exception;

import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConstraintViolationExceptionResponse {

    @Getter
    @Setter
    private class ConstraintViolationResponse {
        private String resource;
        private String field;
        private String message;
    }

    private Integer status;

    private String error;

    private String message;

    private List<ConstraintViolationResponse> violationResponses = new ArrayList<>();

    public void addViolation(ConstraintViolation violation) {
        ConstraintViolationResponse violationResponse = new ConstraintViolationResponse();
        violationResponse.setResource(violation.getRootBeanClass().getName());
        violationResponse.setField(violation.getPropertyPath().toString());
        violationResponse.setMessage(violation.getMessage());
        violationResponses.add(violationResponse);
    }

}