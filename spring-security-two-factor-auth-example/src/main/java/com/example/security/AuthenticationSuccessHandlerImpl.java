package com.example.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.session.LoginUserInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

	private final LoginUserInfo loginUserInfo;

	public AuthenticationSuccessHandlerImpl(LoginUserInfo loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request,
			HttpServletResponse response) {

		if (loginUserInfo.isPassedTwoFactorAuth()) {
			return super.determineTargetUrl(request, response);
		}

		return "/oneTimePassword";
	}
}