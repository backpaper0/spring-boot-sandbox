package com.example;

import org.springframework.security.core.userdetails.User;

public final class Account {

    private final String name;

    public Account(final User user) {
        this.name = user.getUsername();
    }

    public String getName() {
        return name;
    }
}
