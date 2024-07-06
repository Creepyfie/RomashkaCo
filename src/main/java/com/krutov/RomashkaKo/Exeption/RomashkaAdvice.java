package com.krutov.RomashkaKo.Exeption;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RomashkaAdvice {

    @ExceptionHandler(StringIndexOutOfBoundsException.class)
    public ResponseEntity<Response> handleExeption() {
        Response response = new Response("Invalid name or description length", HttpStatusCode.valueOf(415));
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}


