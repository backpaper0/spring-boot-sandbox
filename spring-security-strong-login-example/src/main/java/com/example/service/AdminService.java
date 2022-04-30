package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Account;

@Component
@Transactional
public class AdminService {

	private final JdbcTemplate jdbc;
	private final UserDetailsManager users;

	public AdminService(JdbcTemplate jdbc, UserDetailsManager users) {
		this.jdbc = jdbc;
		this.users = users;
	}

	@Transactional(readOnly = true)
	public List<Account> findAllAccounts() {

		List<Account> accounts = jdbc.query("""
				select
					username,
					locked,
					login_failure_count,
					validity_from,
					validity_to,
					last_loggedin
				from
					accounts
				order by
					username asc
				""", new BeanPropertyRowMapper<>(Account.class));

		return accounts;
	}

	@Transactional(readOnly = true)
	public Account findAccountById(String username) {

		Account account = jdbc.queryForObject("""
				select
					username,
					locked,
					login_failure_count,
					validity_from,
					validity_to,
					last_loggedin
				from
					accounts
				where
					username = ?
				""", new BeanPropertyRowMapper<>(Account.class), username);

		return account;
	}

	public void updateValidity(String username, LocalDate validityFrom, LocalDate validityTo) {
		jdbc.update("""
				update
					accounts
				set
					validity_from = ?,
					validity_to = ?
				where
					username = ?
				""", validityFrom, validityTo, username);
	}

	public void unlock(String username) {
		jdbc.update("""
				update
					accounts
				set
					locked = false,
					login_failure_count = 0
				where
					username = ?
				""", username);
	}
}