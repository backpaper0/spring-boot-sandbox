package com.example.security;

public final class UserInfo {

    private final String token;
    private final String username;

    public UserInfo(final String token, final String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
