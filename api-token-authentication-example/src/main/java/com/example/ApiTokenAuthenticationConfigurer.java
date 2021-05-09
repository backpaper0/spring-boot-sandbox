package com.example;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ApiTokenAuthenticationConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractAuthenticationFilterConfigurer<H, ApiTokenAuthenticationConfigurer<H>, ApiTokenAuthenticationProcessingFilter> {

	public ApiTokenAuthenticationConfigurer() {
		super(new ApiTokenAuthenticationProcessingFilter(), null);
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new RequestMatcher() {
			@Override
			public boolean matches(HttpServletRequest request) {
				String apiToken = request.getHeader("X-Api-Token");
				return apiToken != null && apiToken.isEmpty() == false;
			}
		};
	}
}
