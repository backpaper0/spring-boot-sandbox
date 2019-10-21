package com.example.junit5.hello.impl;

import org.springframework.stereotype.Component;

@Component
public class MessageFormatter {

    public String format(final String template, final Object... args) {
        return String.format(template, args);
    }
}
