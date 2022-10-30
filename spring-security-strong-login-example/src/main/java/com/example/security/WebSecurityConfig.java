package com.example.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
public class WebSecurityConfig {

	@Autowired(required = false)
	private SpringSessionBackedSessionRegistry<?> sessionRegistry;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public ProviderManager authenticationManager(AuthenticationEventPublisher eventPublisher) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		ProviderManager providerManager = new ProviderManager(provider);
		providerManager.setAuthenticationEventPublisher(eventPublisher);
		return providerManager;
	}

	@Bean
	@ConditionalOnBean(JdbcUserDetailsManager.class)
	public InitializingBean jdbcUserDetailsManagerInitializer(JdbcUserDetailsManager jdbcUserDetailsManager) {
		return new InitializingBean() {
			@Override
			public void afterPropertiesSet() {
				// パスワード変更時、現在のパスワードをチェックするために使用する
				jdbcUserDetailsManager.setAuthenticationManager(authenticationManager(null));
			}
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(c -> c
						.requestMatchers("/login").permitAll()
						.requestMatchers("/page1").hasAuthority("AUTH1")
						.requestMatchers("/page2").hasAuthority("AUTH2")
						.requestMatchers("/admin").hasAuthority("ADMIN")
						.anyRequest().authenticated())

				.formLogin(c -> c
						.loginPage("/login")
						.defaultSuccessUrl("/", true))
				.logout(c -> c
						.logoutUrl("/logout"))

				// 二重ログインを禁止する
				.sessionManagement(c -> {
					var maximumSessions = c
							.maximumSessions(1);
					if (sessionRegistry != null) {
						maximumSessions.sessionRegistry(sessionRegistry);
					}
				})

				.authenticationManager(authenticationManager(null))

				.build();
	}
}
