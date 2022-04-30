package com.example.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PasswordService {

	private final UserDetailsManager users;
	private final PasswordEncoder passwordEncoder;

	public PasswordService(UserDetailsManager users, PasswordEncoder passwordEncoder) {
		this.users = users;
		this.passwordEncoder = passwordEncoder;
	}

	public void changePassword(String oldPassword, String newPassword) {
		users.changePassword(oldPassword, passwordEncoder.encode(newPassword));
	}
}