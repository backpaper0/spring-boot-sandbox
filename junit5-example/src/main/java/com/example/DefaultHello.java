package com.example;

import java.util.Objects;

public class DefaultHello implements Hello {

    private final String name;

    public DefaultHello(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String say() {
        return String.format("Hello, %s!", name);
    }
}
