package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class IntegrationExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IntegrationExampleApplication.class, args);
    }
}
