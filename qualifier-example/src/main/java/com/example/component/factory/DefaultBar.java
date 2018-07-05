package com.example.component.factory;

public class DefaultBar implements Bar {

    private final String value;

    public DefaultBar(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
