package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Backend2Application {

    public static void main(String[] args) {
        SpringApplication.run(Backend2Application.class, args);
    }

    @GetMapping("/world")
    String get() {
        return "world!";
    }
}
