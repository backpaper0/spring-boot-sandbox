package com.example.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.session.LoginUserInfo;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

	private LoginUserInfo loginUserInfo;

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