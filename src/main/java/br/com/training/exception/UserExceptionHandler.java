package br.com.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserExceptionHandler {

    private static BindingResult bindingResult;


    @ExceptionHandler(value =  { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserExceptionResponse methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {

        return new UserExceptionResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                appendErrors(ex),
                req.getRequestURI()
        );

    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserExceptionResponse noHanderFoundException(Exception ex, HttpServletRequest req) {

        return new UserExceptionResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                appendErrors(bindingResult),
                req.getRequestURI()
        );

    }

    @ExceptionHandler(value = { MethodNotAllowedException.class })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public UserExceptionResponse methodNotAllowedException(Exception ex, HttpServletRequest req) {

        return new UserExceptionResponse(
                Instant.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
                appendErrors(bindingResult),
                req.getRequestURI()
        );

    }


    private static List<UserExceptionResponse.Details> appendErrors(BindingResult bd) {
        List<UserExceptionResponse.Details> details = new ArrayList<>();
        for (FieldError fe : bd.getFieldErrors()) {
            details.add(new UserExceptionResponse.Details(
                    fe.getField(),
                    fe.getDefaultMessage()));
        }
        for (ObjectError oe : bd.getGlobalErrors()) {
            details.add(new UserExceptionResponse.Details(
                    oe.getObjectName(),
                    oe.getDefaultMessage()));
        }
        return details;
    }
}
