package com.example;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("select username, password, true from account where username = ?");
		users.setAuthoritiesByUsernameQuery("select username, role from account where username = ?");
		users.setGroupAuthoritiesByUsernameQuery("select '', '', '' from account where username = ? and 0 = 1");
		return users;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		return http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login")
				.and().build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
