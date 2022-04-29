package com.example.controller;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Account;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final JdbcTemplate jdbc;
	private final UserDetailsManager users;

	public AdminController(JdbcTemplate jdbc, UserDetailsManager users) {
		this.jdbc = jdbc;
		this.users = users;
	}

	@GetMapping
	public String index(Model model) {

		List<Account> accounts = jdbc.query("""
				select
					username,
					locked,
					password_expiration,
					login_failure_count,
					validity_from,
					validity_to,
					last_loggedin
				from
					accounts
				order by
					username asc
				""", new BeanPropertyRowMapper<>(Account.class));

		model.addAttribute("accounts", accounts);

		System.out.println(accounts);

		return "admin";
	}
}
