package ua.com.novopacksv.production.exception;

import lombok.Getter;
import lombok.Setter;

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

    public void addFieldException(String resource, String field, String message) {
        FieldExceptionResponse fieldException = new FieldExceptionResponse();
        fieldException.setResource(resource);
        fieldException.setField(field);
        fieldException.setMessage(message);
        fieldErrors.add(fieldException);
    }

}