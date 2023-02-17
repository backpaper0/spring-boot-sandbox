package com.example.web.hierarchicalroles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

	private final RoleHierarchy roleHierarchy;

	public WebSecurityConfig(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(c -> c
						.requestMatchers("/foo").access(hasAuthority("A"))
						.requestMatchers("/bar").access(hasAuthority("B"))
						.requestMatchers("/baz").access(hasAuthority("C"))
						.anyRequest().denyAll())
				.build();
	}

	private AuthorityAuthorizationManager<RequestAuthorizationContext> hasAuthority(String role) {
		AuthorityAuthorizationManager<RequestAuthorizationContext> aam = AuthorityAuthorizationManager.hasRole(role);
		aam.setRoleHierarchy(roleHierarchy);
		return aam;
	}
}
