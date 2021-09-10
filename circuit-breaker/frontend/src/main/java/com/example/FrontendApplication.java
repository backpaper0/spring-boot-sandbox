package com.example;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}

@RestController
class HelloController {

    final Hello hello;

    public HelloController(Hello hello) {
        this.hello = hello;
    }

    @GetMapping("/")
    String get() {
        return hello.get();
    }
}

@Component
class Hello {

    final RestTemplate restTemplate;
    final URI backendApi;
    final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public Hello(
            RestTemplate restTemplate,
            @Value("${backend.api}") URI backendApi,
            Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.backendApi = backendApi;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public String get() {
        return circuitBreakerFactory.create("hello").run(() -> restTemplate
                .getForObject(backendApi, String.class), e -> defaultHello());
    }

    String defaultHello() {
        return "Hello, Circuit Breaker!!!";
    }
}