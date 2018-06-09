package com.example.security;

import java.util.Objects;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenStore store;
    private final ApplicationContext applicationContext;

    public TokenAuthenticationProvider(final TokenStore store,
            final ApplicationContext applicationContext) {
        this.store = Objects.requireNonNull(store);
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        final TokenAuthenticationToken token = (TokenAuthenticationToken) authentication;
        final UserInfo userInfo = store.find(token.getToken());
        if (userInfo == null) {
            throw new BadCredentialsException("Bad token");
        }
        final UserDetailsService userDetailsService = applicationContext
                .getBean(UserDetailsService.class);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userInfo.getUsername());
        token.setDetails(userDetails);
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(TokenAuthenticationToken.class);
    }
}
