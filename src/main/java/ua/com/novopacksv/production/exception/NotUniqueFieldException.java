package ua.com.novopacksv.production.exception;

public class NotUniqueFieldException extends RuntimeException {

    public NotUniqueFieldException(String message) {
        super(message);
    }

}