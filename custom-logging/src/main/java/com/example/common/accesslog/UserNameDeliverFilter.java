package com.example.common.accesslog;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class UserNameDeliverFilter implements OrderedFilter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				String userName = authentication.getName();
				request.setAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME, userName);
			}
		}
	}

	@Override
	public int getOrder() {
		return securityProperties.getFilter().getOrder() + 1;
	}
}
