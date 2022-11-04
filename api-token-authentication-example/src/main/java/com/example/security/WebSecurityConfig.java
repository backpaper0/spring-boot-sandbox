package com.example.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@ConfigurationProperties(prefix = "example")
public class WebSecurityConfig {

	private String apiToken;

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(c -> c.anyRequest().authenticated())

				.addFilterAfter(new ApiTokenFilter(apiToken), UsernamePasswordAuthenticationFilter.class)

				.exceptionHandling(c -> c
						.authenticationEntryPoint((request, response, authException) -> {
							if (response.getStatus() == HttpServletResponse.SC_OK) {
								response.sendError(HttpServletResponse.SC_FORBIDDEN);
							}
						})
						.accessDeniedHandler(
								(req, res, e) -> res.setStatus(HttpServletResponse.SC_FORBIDDEN)))

				.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.csrf(c -> c.disable())

				.build();
	}
}
