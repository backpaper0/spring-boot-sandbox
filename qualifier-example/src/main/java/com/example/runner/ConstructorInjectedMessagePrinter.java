package com.example.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.annotation.Tertiary;
import com.example.component.Foo;
import com.example.component.factory.Bar;

@Component
public class ConstructorInjectedMessagePrinter implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Foo foo1;
    private final Foo foo2;
    private final Foo foo3;
    private final Bar bar1;
    private final Bar bar2;
    private final Bar bar3;

    public ConstructorInjectedMessagePrinter(Foo foo1, @Qualifier("secondary") Foo foo2,
            @Tertiary Foo foo3, Bar bar1, @Qualifier("secondary") Bar bar2, @Tertiary Bar bar3) {
        this.foo1 = foo1;
        this.foo2 = foo2;
        this.foo3 = foo3;
        this.bar1 = bar1;
        this.bar2 = bar2;
        this.bar3 = bar3;
    }

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
