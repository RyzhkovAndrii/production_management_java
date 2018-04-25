package ua.com.novopacksv.production.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NotValidExceptionResponse {

    @Getter
    @Setter
    private class FieldExceptionResponse {
        private String resource;
        private String field;
        private String message;
    }

    private Integer status;

    private String error;

    private String message;

    private List<FieldExceptionResponse> fieldErrors = new ArrayList<>();

    public void addFieldException(FieldError fieldError) {
        FieldExceptionResponse fieldException = new FieldExceptionResponse();
        fieldException.setResource(fieldError.getObjectName());
        fieldException.setField(fieldError.getField());
        fieldException.setMessage(fieldError.getDefaultMessage());
        fieldErrors.add(fieldException);
    }

}