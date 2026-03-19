package com.example.postconstruct;

import com.example.Asterisk;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * PostConstructが付いたメソッドはAOPされないっぽい
 *
 */
@Component
@Asterisk
public class PostConstructDemo {

    private static final Logger logger = LoggerFactory.getLogger(PostConstructDemo.class);

    @PostConstruct
    public void init() {
        logger.info("PostConstructDemo#init");
    }

    public void run() {
        logger.info("PostConstructDemo#run");
    }
}
