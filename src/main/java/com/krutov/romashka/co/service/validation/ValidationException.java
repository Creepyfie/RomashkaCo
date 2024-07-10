package com.krutov.romashka.co.service.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

    private final List<ValidationError> errors;

    public ValidationException(ValidationError error) {
        this.errors = List.of(error);
    }
}
