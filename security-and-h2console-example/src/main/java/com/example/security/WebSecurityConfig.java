package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private HandlerMappingIntrospector mvcHandlerMappingIntrospector;
	@Autowired
	private ObjectPostProcessor<Object> opp;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// any request && !h2 console
		AndRequestMatcher requestMatcher = new AndRequestMatcher(
				AnyRequestMatcher.INSTANCE,
				new NegatedRequestMatcher(h2console()));

		return http
				.securityMatcher(requestMatcher)
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults())
				.build();
	}

	@Bean
	SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityMatcher(h2console())
				.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
				.csrf(csrf -> csrf.disable())
				.headers(headers -> {
					headers.frameOptions().sameOrigin();
				})
				.build();
	}

	private MvcRequestMatcher h2console() {
		return opp.postProcess(new MvcRequestMatcher.Builder(mvcHandlerMappingIntrospector)
				.servletPath("/h2-console")
				.pattern("/**"));
	}
}
