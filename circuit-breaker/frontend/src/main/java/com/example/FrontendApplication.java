package com.example;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@EnableCircuitBreaker
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
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

    public Hello(
            RestTemplate restTemplate,
            @Value("${backend.api}") URI backendApi) {
        this.restTemplate = restTemplate;
        this.backendApi = backendApi;
    }

    @HystrixCommand(fallbackMethod = "defaultHello")
    public String get() {
        return restTemplate
                .getForObject(backendApi, String.class);
    }

    String defaultHello() {
        return "Hello, Circuit Breaker!!!";
    }
}