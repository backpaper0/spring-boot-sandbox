package com.example.web.authz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(c -> c
						.requestMatchers("/foo").hasAuthority("FOO")
						.requestMatchers("/bar/**").hasAnyAuthority("BAR")
						.requestMatchers("/baz").hasAnyAuthority("BAZ1", "BAZ2")
						.requestMatchers("/qux").permitAll()
						.anyRequest().denyAll())
				.build();
	}
}
