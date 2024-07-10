package com.krutov.romashka.co.service.validation;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "error")
public class ValidationError {

    ValidationErrorCode code;
    String message;

    public static ValidationError error(ValidationErrorCode code) {
        return error(code, null);
    }
}
