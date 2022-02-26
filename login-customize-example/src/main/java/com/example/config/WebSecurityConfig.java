package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.security.BearerTokenFilter;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests((requests) -> requests.anyRequest().authenticated())
				.formLogin()
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.accessDeniedHandler((req, res, e) -> res.setStatus(HttpStatus.FORBIDDEN.value()))
				.and()
				.addFilterBefore(new BearerTokenFilter(), BasicAuthenticationFilter.class)
				.build();
	}
}
