package com.example;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableResourceServer
@RestController
public class HelloWorldAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldAPIApplication.class, args);
    }

    @GetMapping("/hello")
    String hello(Principal principal) {
        return String.format("Hello, %s!", principal.getName());
    }
}
