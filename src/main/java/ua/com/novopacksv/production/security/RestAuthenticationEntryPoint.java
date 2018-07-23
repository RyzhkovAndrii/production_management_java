package ua.com.novopacksv.production.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ua.com.novopacksv.production.exception.ExceptionResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(convertObjectToJson(getExceptionResponse(e)));
    }

    private ExceptionResponse getExceptionResponse(Exception e) {
        String message = Optional.of(e.getMessage()).orElse("not authenticated");
        ExceptionResponse exception = new ExceptionResponse();
        exception.setStatus(401);
        exception.setError("Unauthorized");
        exception.setMessage(message);
        return exception;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}
