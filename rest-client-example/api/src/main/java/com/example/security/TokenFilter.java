package com.example.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.toLowerCase().startsWith("bearer ")) {
            final String token = authorization.substring("bearer ".length());
            final TokenAuthenticationToken authentication = new TokenAuthenticationToken(
                    token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
