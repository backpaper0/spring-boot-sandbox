package com.example.example5;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("5")
public class SpringValidatorAndBeanValidationExampleController {

    @PostMapping
    public Object post(@RequestBody @Validated Example5Request request) {
        return null;
    }
}
