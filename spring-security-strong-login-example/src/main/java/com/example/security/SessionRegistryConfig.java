package com.example.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@ConditionalOnBean(JdbcIndexedSessionRepository.class)
public class SessionRegistryConfig {

	private JdbcIndexedSessionRepository jdbcIndexedSessionRepository;

	@Bean
	public SpringSessionBackedSessionRegistry<?> sessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(jdbcIndexedSessionRepository);
	}
}
