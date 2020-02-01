package com.example.webmvc.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ModelExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ModelExampleApplication.class, args);
    }
}
