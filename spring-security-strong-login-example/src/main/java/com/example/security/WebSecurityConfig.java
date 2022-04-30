package com.example.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
public class WebSecurityConfig {

	@Bean
	public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		users.setUsersByUsernameQuery("""
				select
					username,
					password,
					true,
					locked,
					case
						when validity_from <= current_timestamp and validity_to >= current_timestamp then false
						when validity_from <= current_timestamp and validity_to is null then false
						else true end,
					case when password_expiration > current_timestamp then false else true end
				from
					accounts
				where
					username = ?
				""");
		users.setAuthoritiesByUsernameQuery("""
				select
					username,
					authority
				from
					authorities
				where
					username = ?
				""");
		users.setEnableGroups(false);

		users.setChangePasswordSql("""
				update
					accounts
				set
					password = ?
				where
					username = ?
				""");

		return users;
	}

	@Bean
	public ProviderManager authenticationManager(UserDetailsService uds) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(uds);
		//		provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	public InitializingBean jdbcUserDetailsManagerInitializer(ProviderManager am, JdbcUserDetailsManager uds) {
		return new InitializingBean() {
			@Override
			public void afterPropertiesSet() {
				// パスワード変更時、現在のパスワードをチェックするために使用する
				uds.setAuthenticationManager(am);
			}
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {
		return http
				.authorizeHttpRequests(c -> c
						.antMatchers("/login").permitAll()
						.antMatchers("/page1").hasAuthority("AUTH1")
						.antMatchers("/page2").hasAuthority("AUTH2")
						.antMatchers("/admin").hasAuthority("ADMIN")
						.anyRequest().authenticated())

				.formLogin(c -> c
						.loginPage("/login")
						.defaultSuccessUrl("/", true))
				.logout(c -> c
						.logoutUrl("/logout"))

				// 二重ログインを禁止する
				.sessionManagement(c -> c
						.maximumSessions(1)
						.sessionRegistry(sessionRegistry(null)))

				.authenticationManager(authenticationManager)

				.build();
	}

	@Bean
	public SpringSessionBackedSessionRegistry<?> sessionRegistry(
			JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
		return new SpringSessionBackedSessionRegistry<>(
				jdbcIndexedSessionRepository);
	}
}
