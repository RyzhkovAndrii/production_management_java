package ua.com.novopacksv.production.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
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

    @ExceptionHandler({NegativeAmountException.class, NotUniqueFieldException.class, RangeException.class})
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ConstraintViolationExceptionResponse response = new ConstraintViolationExceptionResponse();
        response.setStatus(400);
        response.setError("Bad Request");
        response.setMessage("Invalid " + ex.getLocalizedMessage());
        ex.getConstraintViolations().forEach(response::addViolation);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException() throws IOException {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(403);
        response.setError("Forbidden");
        response.setMessage("Access denied");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthException(AuthenticationException ex) {
        String message = Optional.of(ex.getMessage()).orElse("not authenticated");
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(401);
        response.setError("Unauthorized");
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}