package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, SwitchUserFilter switchUserFilter) throws Exception {

		return http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/bar").hasRole("SECRET")
						.requestMatchers("/switch").hasAnyRole("ADMIN", "PREVIOUS_ADMINISTRATOR")
						.requestMatchers("/login/impersonate").hasRole("ADMIN")
						.requestMatchers("/logout/impersonate").hasRole("PREVIOUS_ADMINISTRATOR")
						.anyRequest().authenticated())

				.formLogin(Customizer.withDefaults())

				.addFilter(switchUserFilter)

				.build();
	}

	@Bean
	SwitchUserFilter switchUserFilter(UserDetailsService userDetailsService) {
		SwitchUserFilter filter = new SwitchUserFilter();
		filter.setTargetUrl("/switch");
		filter.setSwitchFailureUrl("/switch?error");
		filter.setUserDetailsService(userDetailsService);
		filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
		return filter;
	}

	@Bean
	InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) {
		UserDetails alice = User.withUsername("alice").password("password").roles("USER", "SECRET")
				.passwordEncoder(passwordEncoder::encode).build();
		UserDetails bob = User.withUsername("bob").password("password").roles("USER")
				.passwordEncoder(passwordEncoder::encode).build();
		UserDetails admin = User.withUsername("admin").password("password").roles("USER", "ADMIN")
				.passwordEncoder(passwordEncoder::encode).build();
		return new InMemoryUserDetailsManager(alice, bob, admin);
	}

	@Bean
	Pbkdf2PasswordEncoder passwordEncoder() {
		Pbkdf2PasswordEncoder passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		passwordEncoder.setEncodeHashAsBase64(true);
		return passwordEncoder;
	}
}
