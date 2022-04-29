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
					case when current_timestamp between validity_from and validity_to then false else true end,
					case when password_expiration > current_timestamp then false else true end
				from
					accounts
				where
					username = ?
				""");
		users.setAuthoritiesByUsernameQuery("""
				select
					username,
					authority
				from
					authorities
				where
					username = ?
				""");
		users.setEnableGroups(false);
		return users;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(c -> c
						.antMatchers("/login").permitAll()
						.antMatchers("/page1").hasAuthority("AUTH1")
						.antMatchers("/page2").hasAuthority("AUTH2")
						.antMatchers("/admin").hasAuthority("ADMIN")
						.anyRequest().authenticated())

				.formLogin(c -> c
						.loginPage("/login")
						.defaultSuccessUrl("/", true))
				.logout(c -> c
						.logoutUrl("/logout"))

				// 二重ログインを禁止する
				.sessionManagement(c -> c
						.maximumSessions(1)
						.sessionRegistry(sessionRegistry(null)))

				.build();
	}

	@Bean
	public SpringSessionBackedSessionRegistry<?> sessionRegistry(
			JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
		return new SpringSessionBackedSessionRegistry<>(
				jdbcIndexedSessionRepository);
	}
}
