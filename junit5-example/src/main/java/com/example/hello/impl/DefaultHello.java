package com.example.hello.impl;

import java.util.Objects;

import com.example.hello.Hello;

public class DefaultHello implements Hello {

    private final MessageFormatter messageFormatter;
    private final String name;

    public DefaultHello(final MessageFormatter messageFormatter, final String name) {
        this.name = Objects.requireNonNull(name);
        this.messageFormatter = Objects.requireNonNull(messageFormatter);
    }

    @Override
    public String say() {
        return messageFormatter.format("Hello, %s!", name);
    }
}
