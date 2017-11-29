package com.example.fn;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class FnApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FnApplication.class, args);
    }

    @Bean
    public Function<String, String> hello() {
        return s -> String.format("Hello, %s!", s);
    }

    @Bean
    public UnaryOperator<Flux<String>> upper() {
        return flux -> flux.map(String::toUpperCase);
    }
}
