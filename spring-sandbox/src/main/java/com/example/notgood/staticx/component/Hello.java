package com.example.notgood.staticx.component;

public class Hello {

    private final String template;

    public Hello(final String template) {
        this.template = template;
    }

    public String say(final String yourName) {
        return String.format(template, yourName);
    }
}
