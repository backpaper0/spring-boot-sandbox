package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ApiTokenAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private InMemoryApiTokenRepository repository;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String apiToken = (String) authentication.getCredentials();
		String username = repository.get(apiToken);
		if (username != null) {
			return new ApiTokenAuthenticationToken(username, apiToken);
		}
		throw new ApiTokenAuthenticationException();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return ApiTokenAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
