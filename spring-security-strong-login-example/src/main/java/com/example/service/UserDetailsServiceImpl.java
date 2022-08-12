package com.example.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class UserDetailsServiceImpl {

	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("""
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
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("""
				select
					username,
					authority
				from
					authorities
				where
					username = ?
				""");
		jdbcUserDetailsManager.setEnableGroups(false);
		jdbcUserDetailsManager.setChangePasswordSql("""
				update
					account_passwords
				set
					password = ?
				where
					username = ?
				""");
		return jdbcUserDetailsManager;
	}
}
