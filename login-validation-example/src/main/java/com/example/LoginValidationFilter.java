package com.example;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginValidationFilter extends OncePerRequestFilter {

    private final Validator validator;
    private final RequestMatcher requestMatcher;
    private final Class<?> beanType;

    public LoginValidationFilter(final Validator validator, final String loginProcessingUrl,
            final Class<?> beanType) {
        this.validator = validator;
        this.requestMatcher = new AntPathRequestMatcher(loginProcessingUrl, "POST");
        this.beanType = beanType;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {

        if (requestMatcher.matches(request)) {
            final Set<ConstraintViolation<?>> errors = new LinkedHashSet<>();
            errors.addAll(validator.validateValue(beanType,
                    UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY,
                    request.getParameter(
                            UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)));
            errors.addAll(validator.validateValue(beanType,
                    UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY,
                    request.getParameter(
                            UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)));
            if (errors.isEmpty() == false) {
                //TODO
            }
        }

        filterChain.doFilter(request, response);
    }
}
