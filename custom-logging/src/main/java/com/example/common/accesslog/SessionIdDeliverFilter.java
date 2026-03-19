package com.example.common.accesslog;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.servlet.filter.OrderedFilter;
import org.springframework.stereotype.Component;

@Component
public class SessionIdDeliverFilter implements OrderedFilter {

    @Value("${spring.session.servlet.filter-order:#{T(Integer).MIN_VALUE + 50}}")
    private int sessionFilterOrder;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                String id = session.getId();
                request.setAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME, id);
            }
        }
    }

    @Override
    public int getOrder() {
        return sessionFilterOrder + 1;
    }
}
