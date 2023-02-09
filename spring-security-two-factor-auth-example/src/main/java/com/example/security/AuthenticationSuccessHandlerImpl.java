package com.example.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.session.LoginUserInfo;

public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

	private final LoginUserInfo loginUserInfo;

	public AuthenticationSuccessHandlerImpl(LoginUserInfo loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
		setDefaultTargetUrl("/home");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		if (hasTrust(request)) {
			loginUserInfo.passTwoFactorAuth();
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

	private static boolean hasTrust(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("TRUSTME") && cookie.getValue().equals("asdfghjk")) {
					return true;
				}
			}
		}
		return false;
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