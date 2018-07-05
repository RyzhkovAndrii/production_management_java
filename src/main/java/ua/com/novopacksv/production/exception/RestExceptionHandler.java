package ua.com.novopacksv.production.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFound(ResourceNotFoundException ex) {
        String message = Optional.of(ex.getMessage()).orElse(ex.getClass().getSimpleName());
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(404);
        response.setError("Not Found");
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NegativeAmountException.class, NotUniqueFieldException.class, RangeException.class, NotAvailableColorException.class})
    public ResponseEntity<ExceptionResponse> handleIncorrectResponseDataException(Exception ex) {
        String message = Optional.of(ex.getMessage()).orElse(ex.getClass().getSimpleName());
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<NotValidExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        NotValidExceptionResponse response = new NotValidExceptionResponse();
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage("Invalid " + ex.getBindingResult().getObjectName());
        ex.getBindingResult().getFieldErrors().forEach(response::addFieldException);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}