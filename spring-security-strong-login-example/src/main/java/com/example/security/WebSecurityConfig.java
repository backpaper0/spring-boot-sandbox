package com.example.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
public class WebSecurityConfig {

	@Bean
	public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("""
				select
					username,
					password,
					true,
					locked,
					case when current_timestamp between active_from and active_to then false else true end,
					case when password_expire > current_timestamp then false else true end
				from
					users
				where
					username = ?
				""");
		users.setAuthoritiesByUsernameQuery("""
				select
					username,
					authority
				from
					users
				where
					username = ?
				""");
		users.setEnableGroups(false);
		return users;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				.and().formLogin()
				// 二重ログインを禁止する
				.and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry(null)).and()
				.and().build();
	}

	@Bean
	public SpringSessionBackedSessionRegistry<?> sessionRegistry(
			JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
		return new SpringSessionBackedSessionRegistry<>(
				jdbcIndexedSessionRepository);
	}
}
