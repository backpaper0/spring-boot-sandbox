package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Hello {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Logging
    public void say() {
        logger.info("Hello, world!");
    }
}
