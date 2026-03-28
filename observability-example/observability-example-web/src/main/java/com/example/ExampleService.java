package com.example;

import io.micrometer.observation.annotation.Observed;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private Logger logger = LoggerFactory.getLogger(ExecutorService.class);

    @Observed
    public String sayHello(String name) {
        logger.info("sayHello: name={}", name);
        return "Hello, " + name + "!";
    }
}
