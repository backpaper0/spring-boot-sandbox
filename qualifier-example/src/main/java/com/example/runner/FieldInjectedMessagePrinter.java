package com.example.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.annotation.Tertiary;
import com.example.component.Foo;
import com.example.component.factory.Bar;

@Component
public class FieldInjectedMessagePrinter implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Foo foo1;

    @Autowired
    @Qualifier("secondary")
    private Foo foo2;

    @Autowired
    @Tertiary
    private Foo foo3;

    @Autowired
    private Bar bar1;

    @Autowired
    @Qualifier("secondary")
    private Bar bar2;

    @Autowired
    @Tertiary
    private Bar bar3;

    @Override
    public void run(String... args) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("********************");
            logger.info("{}", foo1);
            logger.info("{}", foo2);
            logger.info("{}", foo3);
            logger.info("{}", bar1);
            logger.info("{}", bar2);
            logger.info("{}", bar3);
            logger.info("********************");
        }
    }
}
