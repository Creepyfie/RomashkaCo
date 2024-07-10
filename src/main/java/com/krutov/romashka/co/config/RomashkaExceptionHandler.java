package com.krutov.romashka.co.config;

import com.krutov.romashka.co.service.validation.ValidationError;
import com.krutov.romashka.co.service.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RomashkaExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<ValidationError>> handleException(ValidationException e) {
        return new ResponseEntity<>(e.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


