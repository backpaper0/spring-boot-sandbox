package com.example;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    @Observed
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
