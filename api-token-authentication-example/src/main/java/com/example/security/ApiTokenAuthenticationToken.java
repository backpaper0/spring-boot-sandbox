package com.example.security;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiTokenAuthenticationToken extends AbstractAuthenticationToken {

	private final String username;
	private String apiToken;

	public ApiTokenAuthenticationToken(String username, String apiToken) {
		super(List.of());
		this.username = username;
		this.apiToken = apiToken;
		setAuthenticated(username != null);
	}

	@Override
	public Object getCredentials() {
		return apiToken;
	}

	@Override
	public Object getPrincipal() {
		return username;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		apiToken = null;
	}
}
