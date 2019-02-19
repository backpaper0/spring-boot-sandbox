package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MetricsExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MetricsExampleApplication.class, args);
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }
}
