package com.example.security.method;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ProtectedHello {

    @PreAuthorize("authenticated")
    public String say() {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return String.format("Hello, %s!", authentication.getName());
    }
}
