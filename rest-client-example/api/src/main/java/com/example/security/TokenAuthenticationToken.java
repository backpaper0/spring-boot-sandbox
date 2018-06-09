package com.example.security;

import java.util.Collections;
import java.util.Objects;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class TokenAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public TokenAuthenticationToken(final String token) {
        super(Collections.emptyList());
        this.token = Objects.requireNonNull(token);
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return getDetails();
    }
}
