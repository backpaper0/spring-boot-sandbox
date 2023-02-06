package com.example.security;

import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.example.session.LoginUserInfo;

public class TwoFactorAuthAuthorizationManager
		implements AuthorizationManager<RequestAuthorizationContext> {

	private LoginUserInfo loginUserInfo;

	public TwoFactorAuthAuthorizationManager(LoginUserInfo loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
	}

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication,
			RequestAuthorizationContext object) {
		return new AuthorizationDecision(loginUserInfo.isPassedTwoFactorAuth());
	}
}
