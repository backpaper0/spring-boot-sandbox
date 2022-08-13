package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MockUserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

		userDetailsManager.createUser(User.withUsername("mock01")
				.password("pass")
				.passwordEncoder(passwordEncoder::encode)
				.authorities("AUTH1", "AUTH2", "ADMIN")
				.build());

		return userDetailsManager;
	}
}
