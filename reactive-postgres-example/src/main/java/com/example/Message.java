package com.example;

public final class Message {

    private final Integer id;
    private final String text;

    public Message(final Integer id, final String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
