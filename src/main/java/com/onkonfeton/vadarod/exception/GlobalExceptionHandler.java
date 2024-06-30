package com.onkonfeton.vadarod.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankApiCallException.class)
    public ResponseEntity<ExceptionMessage> handleBankApiCallException(BankApiCallException e) {
        return new ResponseEntity<>(
                new ExceptionMessage(HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()
                ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
                new ExceptionMessage(HttpStatus.BAD_REQUEST.value(),
                        String.format("Invalid value [%s] for parameter %s", e.getValue(), e.getPropertyName())
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
