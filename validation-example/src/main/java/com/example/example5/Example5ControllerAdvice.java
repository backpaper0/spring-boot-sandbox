package com.example.example5;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

@RestControllerAdvice(basePackageClasses = SpringValidatorAndBeanValidationExampleController.class)
public class Example5ControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, NativeWebRequest request) {
        webDataBinder.addValidators(new Example5SmartValidator(request));
    }

    @ExceptionHandler(BindException.class)
    ProblemDetail handle(BindException e, final Locale locale) {
        Map<String, List<String>> fieldErrors = e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        a -> a.getField(),
                        Collectors.mapping(a -> messageSource.getMessage(a, locale), Collectors.toList())));
        List<String> globalErrors = e.getGlobalErrors().stream()
                .map(a -> messageSource.getMessage(a, locale))
                .toList();
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("fieldErrors", fieldErrors);
        problemDetail.setProperty("globalErrors", globalErrors);
        return problemDetail;
    }
}
