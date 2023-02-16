package com.example.web.authz;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		List<UserDetails> users = List.of(
				User.withUsername("user1").authorities("FOO").password("secret").build(),
				User.withUsername("user2").authorities("BAR").password("secret").build(),
				User.withUsername("user3").authorities("BAZ").password("secret").build(),
				User.withUsername("user4").authorities("FOO", "BAR", "BAZ").password("secret").build());
		return new InMemoryUserDetailsManager(users);
	}
}
