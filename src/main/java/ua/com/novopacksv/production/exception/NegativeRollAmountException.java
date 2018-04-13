package ua.com.novopacksv.production.exception;

public class NegativeRollAmountException extends RuntimeException {

    public NegativeRollAmountException(String message) {
        super(message);
    }
}
