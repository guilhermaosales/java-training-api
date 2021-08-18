package br.com.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value =  { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserExceptionResponse methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {

        List<String> errors = appendErrors(ex);

        return new UserExceptionResponse(
                Instant.now(),
                "400",
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors,
                req.getRequestURI()
        );
    }

    private static List<String> appendErrors(BindException bd) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : bd.getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError fieldError : bd.getGlobalErrors()) {
            errors.add(fieldError.getObjectName() + ": " + fieldError.getDefaultMessage());
        }
        return errors;
    }
}
