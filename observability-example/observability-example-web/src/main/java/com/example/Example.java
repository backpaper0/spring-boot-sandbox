package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Example {

    private final Logger logger = LoggerFactory.getLogger(Example.class);

    @Autowired
    private ExampleService service;

    @Scheduled(cron = "*/10 * * * * *")
    public void run() {
        logger.info(service.sayHello("world"));
    }
}
