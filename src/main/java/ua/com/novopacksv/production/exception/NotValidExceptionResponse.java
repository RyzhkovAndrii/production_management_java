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
        private String field;
        private String message;
    }

    private Integer status;

    private String error;

    private List<FieldExceptionResponse> exceptions = new ArrayList<>();

    public void addFieldException(String field, String message) {
        FieldExceptionResponse fieldException = new FieldExceptionResponse();
        fieldException.setField(field);
        fieldException.setMessage(message);
        exceptions.add(fieldException);
    }

}