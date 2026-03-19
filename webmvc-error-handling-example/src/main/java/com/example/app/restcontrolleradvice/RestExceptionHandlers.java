package com.example.app.restcontrolleradvice;

import com.example.app.exception.HandlingException;
import java.util.Map;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.app.restcontroller")
public class RestExceptionHandlers {

    @ExceptionHandler(HandlingException.class)
    public Object handleHandlingException() {
        return Map.of("message", "error");
    }
}
