package com.example.security;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiTokenFilter extends OncePerRequestFilter {

	private final String apiToken;

	public ApiTokenFilter(String apiToken) {
		this.apiToken = apiToken;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorization != null && authorization.toLowerCase().startsWith("bearer ")) {
			String apiToken = authorization.substring("bearer ".length());
			if (Objects.equals(apiToken, this.apiToken)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("test",
						"", List.of());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
