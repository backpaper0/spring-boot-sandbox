package com.example.security;

import java.util.Objects;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final TokenStore store;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public TokenController(final TokenStore store, final UserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder) {
        this.store = Objects.requireNonNull(store);
        this.userDetailsService = Objects.requireNonNull(userDetailsService);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @PostMapping("/token")
    public String token(@RequestParam final String username, @RequestParam final String password) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.encode(password).equals(userDetails.getPassword())) {
            final String token = UUID.randomUUID().toString();
            store.save(new UserInfo(token, username));
            return token;
        }
        throw new UsernameNotFoundException("");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void handle(final UsernameNotFoundException e) {
    }
}
