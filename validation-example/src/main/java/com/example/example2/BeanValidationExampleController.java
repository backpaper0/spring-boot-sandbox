package com.example.example2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter
 */
@RestController
@RequestMapping("/2")
public class BeanValidationExampleController {

    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public String post(@Valid final ExampleForm form) {
        return "Valid";
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    String handle(BindException e, Locale locale) {
        return e.getAllErrors().stream()
                .map(a -> messageSource.getMessage(a, locale))
                .collect(Collectors.joining("\n"));
    }
}

class ExampleForm {

    @NotNull
    @Size(min = 1)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
