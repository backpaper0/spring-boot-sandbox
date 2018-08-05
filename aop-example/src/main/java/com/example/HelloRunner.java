package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HelloRunner implements CommandLineRunner {

    private final Hello hello;

    public HelloRunner(final Hello hello) {
        this.hello = hello;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println(hello.say());
    }
}
