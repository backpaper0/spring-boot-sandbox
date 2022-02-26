package com.example.globalmethodsecurityexample;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

		UnaryOperator<String> encoder = PasswordEncoderFactories
				.createDelegatingPasswordEncoder()::encode;

		Function<String, UserBuilder> builder = username -> User.withUsername(username)
				.password("secret").passwordEncoder(encoder);

		return new InMemoryUserDetailsManager(
				builder.apply("foo").roles("FOO", "BAR").build(),
				builder.apply("bar").roles("BAR").build(),
				builder.apply("baz").roles("BAZ").build());
	}
}
