package com.example.security;

import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

public class TokenConfig extends AbstractHttpConfigurer<TokenConfig, HttpSecurity> {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final ApplicationContext applicationContext = http
                .getSharedObject(ApplicationContext.class);
        final TokenStore tokenStore = applicationContext.getBean(TokenStore.class);
        http.addFilterBefore(new TokenFilter(), AnonymousAuthenticationFilter.class)
                .authenticationProvider(
                        new TokenAuthenticationProvider(tokenStore, applicationContext));
    }

    public static TokenConfig token() {
        return new TokenConfig();
    }
}
