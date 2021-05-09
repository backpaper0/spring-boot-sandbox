package com.example;

import org.springframework.security.core.AuthenticationException;

public class ApiTokenAuthenticationException extends AuthenticationException {

	public ApiTokenAuthenticationException() {
		super("Invalid API token");
	}
}
