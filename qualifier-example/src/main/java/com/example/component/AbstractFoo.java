package com.example.component;

public class AbstractFoo implements Foo {

    private final String value;

    public AbstractFoo(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
