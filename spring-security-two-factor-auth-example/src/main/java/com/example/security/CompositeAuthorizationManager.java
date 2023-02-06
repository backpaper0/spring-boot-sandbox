package com.example.security;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

public class CompositeAuthorizationManager<T> implements AuthorizationManager<T> {

	private final List<AuthorizationManager<T>> authorizationManagers;

	private CompositeAuthorizationManager(List<AuthorizationManager<T>> authorizationManagers) {
		this.authorizationManagers = authorizationManagers;
	}

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, T object) {
		return new AuthorizationDecision(authorizationManagers.stream()
				.map(am -> am.check(authentication, object))
				.allMatch(AuthorizationDecision::isGranted));
	}

	@SafeVarargs
	public static <T> CompositeAuthorizationManager<T> composite(
			AuthorizationManager<T>... authorizationManagers) {
		return new CompositeAuthorizationManager<>(List.of(authorizationManagers));
	}
}
