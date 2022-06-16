package com.example.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ApiTokenAuthenticationConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractAuthenticationFilterConfigurer<H, ApiTokenAuthenticationConfigurer<H>, ApiTokenAuthenticationProcessingFilter> {

	private String headerName = "X-Api-Token";

	public ApiTokenAuthenticationConfigurer() {
		super(new ApiTokenAuthenticationProcessingFilter(), null);
		getAuthenticationFilter().setHeaderName(headerName);
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new RequestMatcher() {
			@Override
			public boolean matches(HttpServletRequest request) {
				String apiToken = request.getHeader(headerName);
				return apiToken != null && apiToken.isEmpty() == false;
			}
		};
	}

	public ApiTokenAuthenticationConfigurer<H> headerName(String headerName) {
		this.headerName = headerName;
		getAuthenticationFilter().setHeaderName(headerName);
		return this;
	}
}
