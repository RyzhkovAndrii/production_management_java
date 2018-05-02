package ua.com.novopacksv.production.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

    private Integer status;

    private String error;

    private String message;

}