package com.example.error;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.boot.webmvc.autoconfigure.error.ErrorViewResolver;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorViewResolverImpl implements ErrorViewResolver, Ordered {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

        if (status == HttpStatus.FORBIDDEN) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null
                    || authentication instanceof AnonymousAuthenticationToken
                    || !authentication.isAuthenticated()) {
                return new ModelAndView("redirect:/login");
            }
        }

        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
