package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
		InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager(
				User.withUsername("alice").password(passwordEncoder().encode("secret")).authorities("USER").build());
		DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
		ap.setPasswordEncoder(passwordEncoder());
		ap.setUserDetailsService(uds);
		return http
				.requestMatchers(c -> c.antMatchers("/customers/**"))
				.authorizeHttpRequests(c -> c.anyRequest().authenticated())
				.authenticationProvider(ap)
				.csrf(c -> c.disable())
				.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(c -> c.and())
				.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
		UserDetailsService uds = new InMemoryUserDetailsManager(
				User.withUsername("bob").password(passwordEncoder().encode("secret")).authorities("USER").build());
		DaoAuthenticationProvider ap = new DaoAuthenticationProvider();
		ap.setPasswordEncoder(passwordEncoder());
		ap.setUserDetailsService(uds);
		return http
				.requestMatchers(c -> c.antMatchers("/orders/**"))
				.authorizeHttpRequests(c -> c.anyRequest().authenticated())
				.authenticationProvider(ap)
				.csrf(c -> c.disable())
				.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(c -> c.and())
				.build();
	}
}
