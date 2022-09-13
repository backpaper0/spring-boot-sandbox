package com.example.security;

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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(c -> c
						.mvcMatchers("/foo").hasAuthority("FOO")
						.mvcMatchers("/bar/**").hasAnyAuthority("BAR")
						.mvcMatchers("/baz").hasAnyAuthority("BAZ1", "BAZ2")
						.mvcMatchers("/qux").permitAll()
						.anyRequest().denyAll())
				.build();
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		List<UserDetails> users = List.of(
				User.withUsername("user1").authorities("FOO").password("secret").build(),
				User.withUsername("user2").authorities("BAR").password("secret").build(),
				User.withUsername("user3").authorities("BAZ").password("secret").build(),
				User.withUsername("user4").authorities("FOO", "BAR", "BAZ").password("secret").build());
		return new InMemoryUserDetailsManager(users);
	}
}
