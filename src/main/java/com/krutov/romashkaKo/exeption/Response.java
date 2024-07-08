package com.krutov.romashkaKo.exeption;

import lombok.Value;
import org.springframework.http.HttpStatusCode;

@Value
public class Response {
    String errorMessage;
    HttpStatusCode httpStatus;
}
