package com.example;

import io.micrometer.observation.annotation.Observed;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Observed
public class ExampleController {

    private final Logger logger = LoggerFactory.getLogger(ExampleController.class);
    private final ExampleService service;

    public ExampleController(ExampleService service) {
        this.service = service;
    }

    @GetMapping
    public Object get() {
        logger.info("start `get`");
        String message = service.sayHello("world");
        var returnValue = Map.of("message", message);
        logger.info("end `get`");
        return returnValue;
    }
}
