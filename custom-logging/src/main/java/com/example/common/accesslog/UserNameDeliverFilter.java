package com.example.common.accesslog;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.servlet.filter.OrderedFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserNameDeliverFilter implements OrderedFilter {

    @Value("${spring.security.filter.order:-100}")
    private int securityFilterOrder;

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
        return securityFilterOrder + 1;
    }
}
