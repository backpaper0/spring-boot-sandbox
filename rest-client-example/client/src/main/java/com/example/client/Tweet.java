package com.example.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {

    private final String username;
    private final String text;

    public Tweet(@JsonProperty("username") final String username,
            @JsonProperty("text") final String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }
}
