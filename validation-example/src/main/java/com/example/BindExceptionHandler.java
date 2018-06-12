package com.example;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BindExceptionHandler {

    private final MessageSource messageSource;

    public BindExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BindException.class)
    List<String> handle(final BindException e) {
        return e.getAllErrors().stream()
                .map(a -> messageSource.getMessage(a, Locale.getDefault()))
                .collect(Collectors.toList());
    }
}
