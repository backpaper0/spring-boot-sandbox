package com.example.webmvc.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelExampleController {

    @ModelAttribute
    ModelExampleModel model() {
        return new ModelExampleModel("default");
    }

    @GetMapping("/")
    ModelExampleModel home(final ModelExampleModel model) {
        return model;
    }
}
