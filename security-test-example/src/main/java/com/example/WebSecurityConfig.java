package com.example;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests()
				.antMatchers("/foo").permitAll()
				.antMatchers("/bar").hasRole("BAR")
				.antMatchers("/baz").hasAuthority("ROLE_BAZ")
				.anyRequest().authenticated()

				.and().httpBasic()

				.and().csrf().disable()
				.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

		Supplier<UserBuilder> builder = () -> User.builder()
				.passwordEncoder(
						PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

		return new InMemoryUserDetailsManager(
				builder.get().username("foo").password("foo").roles("FOO").build(),
				builder.get().username("bar").password("bar").roles("BAR").build(),
				builder.get().username("baz").password("baz").roles("BAZ").build(),
				builder.get().username("qux").password("qux").roles("QUX").build());
	}
}
