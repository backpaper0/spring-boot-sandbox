package com.example.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
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
					a.username,
					p.password,
					true,
					a.locked,
					case
						when a.validity_from <= current_timestamp and a.validity_to >= current_timestamp then false
						when a.validity_from <= current_timestamp and a.validity_to is null then false
						else true end,
					case when p.password_expiration > current_timestamp then false else true end
				from
					accounts a
				inner join
					account_passwords p
				on
					a.username = p.username
				where
					a.username = ?
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
					account_passwords
				set
					password = ?
				where
					username = ?
				""");

		return users;
	}

	@Bean
	public Pbkdf2PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	@Bean
	public ProviderManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService(null));
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	public InitializingBean jdbcUserDetailsManagerInitializer() {
		return new InitializingBean() {
			@Override
			public void afterPropertiesSet() {
				// パスワード変更時、現在のパスワードをチェックするために使用する
				userDetailsService(null).setAuthenticationManager(authenticationManager());
			}
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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

				.authenticationManager(authenticationManager())

				.build();
	}

	@Bean
	public SpringSessionBackedSessionRegistry<?> sessionRegistry(
			JdbcIndexedSessionRepository jdbcIndexedSessionRepository) {
		return new SpringSessionBackedSessionRegistry<>(
				jdbcIndexedSessionRepository);
	}
}
