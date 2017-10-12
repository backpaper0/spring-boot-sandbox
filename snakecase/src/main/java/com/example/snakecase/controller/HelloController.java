package com.example.snakecase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.snakecase.bind.Snake2Camel;

@RestController
public class HelloController {

    @GetMapping("/")
    public String get(@Snake2Camel final HelloModel model) {
        return model.getFooBar();
    }
}
