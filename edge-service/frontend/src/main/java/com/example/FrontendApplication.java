package com.example;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
        return new RestTemplate();
    }
}

@RestController
class FrontController {

    final RestTemplate restTemplate;

    @Value("${hello.api}")
    private URI helloUrl;
    @Value("${world.api}")
    private URI worldUrl;

    public FrontController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    String get() {
        String hello = restTemplate.getForObject(helloUrl.resolve("/hello"), String.class);
        String world = restTemplate.getForObject(worldUrl.resolve("/world"), String.class);
        return hello + world;
    }
}
