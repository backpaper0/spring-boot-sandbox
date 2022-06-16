package com.example.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests(c -> c.antMatchers("/login").not().authenticated()
						.anyRequest().authenticated())

				.apply(new ApiTokenAuthenticationConfigurer<>())
				.successHandler((req, resp, auth) -> {
				})
				.failureHandler((req, resp, e) -> {
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				})
				.and()

				.exceptionHandling(c -> c
						.authenticationEntryPoint((request, response, authException) -> {
							if (response.getStatus() == HttpServletResponse.SC_OK) {
								response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
							}
						})
						.accessDeniedHandler(
								(req, res, e) -> res.setStatus(HttpServletResponse.SC_FORBIDDEN)))

				.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.csrf(c -> c.disable())

				.build();
	}
}
