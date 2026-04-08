package com.example.example1;

import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1")
public class SpringValidatorExampleController {

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    void init(final WebDataBinder binder) {
        binder.setValidator(new ExampleFormValidator());
    }

    @PostMapping
    public String post(@Validated final ExampleForm form) {
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

    private String text;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}

class ExampleFormValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return ExampleForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final ExampleForm form = (ExampleForm) target;
        if (form.getText() == null || form.getText().isEmpty()) {
            errors.rejectValue("text", "required");
        }
    }
}
