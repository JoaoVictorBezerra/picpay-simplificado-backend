package com.joaovictor.picpaysimplificado.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Log4j2
public class RestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ProblemDetail handleException(Exception ex , WebRequest request){
        log.error("Error in handleException: {} and request, {}", ex.getMessage(), request);
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Thrown Exception!");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleIncorrectPasswordOrEmail(BusinessException exception) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(exception.getTitle());
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<InvalidParam> fieldErrors = e.getFieldErrors()
              .stream()
              .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
              .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Your request parameters didn't validate.");
        problemDetail.setProperty("invalid-params", fieldErrors);

        return problemDetail;
    }

    private record InvalidParam(String fieldName, String reason) {

    }
}
