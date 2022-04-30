package com.example.service;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PasswordService {

	private final UserDetailsManager users;

	public PasswordService(UserDetailsManager users) {
		this.users = users;
	}

	public void changePassword(String oldPassword, String newPassword) {
		users.changePassword(oldPassword,
				PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(newPassword));
	}
}