package com.example.example5;

import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.context.request.NativeWebRequest;

public class Example5SmartValidator implements SmartValidator {

    private final NativeWebRequest request;

    public Example5SmartValidator(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Example5Request.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validate(target, errors, new Object[0]);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        var baz = request.getParameter("baz");
        if (baz == null) {
            errors.reject("baz.error");
        }
    }
}
