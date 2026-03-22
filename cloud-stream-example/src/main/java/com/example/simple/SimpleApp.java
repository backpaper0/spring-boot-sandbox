package com.example.simple;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleApp {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApp.class, args);
    }

    @Bean
    Function<String, String> uppercase() {
        return s -> s.toUpperCase();
    }

    @Bean
    Function<String, String> reverse() {
        return s -> new StringBuilder(s).reverse().toString();
    }
}
