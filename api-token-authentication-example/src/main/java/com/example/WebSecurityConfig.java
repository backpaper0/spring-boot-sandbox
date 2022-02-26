package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests()
				.antMatchers("/login").not().authenticated()
				.anyRequest().authenticated()

				.and()
				.apply(new ApiTokenAuthenticationConfigurer<>())
				.successHandler((req, resp, auth) -> {
				})
				.failureHandler((req, resp, e) -> {
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				})

				.and().exceptionHandling()
				.authenticationEntryPoint(new AuthenticationEntryPoint() {
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException authException)
							throws IOException, ServletException {
						if (response.getStatus() == HttpServletResponse.SC_OK) {
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						}
					}
				})
				.accessDeniedHandler(
						(req, res, e) -> res.setStatus(HttpServletResponse.SC_FORBIDDEN))

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				.csrf().disable()

				.build();
	}
}
