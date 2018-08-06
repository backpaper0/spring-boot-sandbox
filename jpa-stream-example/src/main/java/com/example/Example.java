package com.example;

import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Example implements CommandLineRunner {

    private final MessageService service;

    public Example(final MessageService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Override
    public void run(final String... args) throws Exception {
        service.setUpData();
        service.printData();
    }
}
