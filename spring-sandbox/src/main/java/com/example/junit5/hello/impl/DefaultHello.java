package com.example.junit5.hello.impl;

import com.example.junit5.hello.Hello;
import java.util.Objects;

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
