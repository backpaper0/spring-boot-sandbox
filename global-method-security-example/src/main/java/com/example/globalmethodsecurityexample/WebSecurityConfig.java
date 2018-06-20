package com.example.globalmethodsecurityexample;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        final UnaryOperator<String> encoder = PasswordEncoderFactories
                .createDelegatingPasswordEncoder()::encode;
        final Function<String, UserBuilder> builder = username -> User.withUsername(username)
                .password("secret").passwordEncoder(encoder);
        auth.inMemoryAuthentication()
                .withUser(builder.apply("foo").roles("FOO", "BAR"))
                .withUser(builder.apply("bar").roles("BAR"))
                .withUser(builder.apply("baz").roles("BAZ"));
    }
}
