package com.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RequestInjectionExperimentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestInjectionExperimentApplication.class, args);
    }

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public String getRequestClassName() {
        return request.getClass().getName();
    }
}
