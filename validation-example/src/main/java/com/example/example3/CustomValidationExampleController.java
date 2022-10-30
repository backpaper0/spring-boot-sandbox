package com.example.example3;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("/3")
public class CustomValidationExampleController {

    @PostMapping
    public String post(@Valid final ExampleForm form) {
        return "Valid";
    }
}

class ExampleForm {

    @Example
    @Size(max = 5)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}

@RestControllerAdvice(basePackageClasses = CustomValidationExampleController.class)
class ExampleAdvice {

    private final Validator validator = new ValidatorImpl();

    @InitBinder
    public void init(final WebDataBinder binder) {
        binder.addValidators(validator);
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Example {
}

class ValidatorImpl implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        for (final Field field : clazz.getDeclaredFields()) {
            final Example example = AnnotationUtils.findAnnotation(field, Example.class);
            if (example != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        for (final Field field : target.getClass().getDeclaredFields()) {
            final Example example = AnnotationUtils.findAnnotation(field, Example.class);
            if (example != null) {
                ReflectionUtils.makeAccessible(field);
                final Object value = ReflectionUtils.getField(field, target);
                if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                    errors.rejectValue(field.getName(), "required");
                }
            }
        }
    }
}
