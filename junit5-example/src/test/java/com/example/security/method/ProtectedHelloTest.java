package com.example.security.method;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(ProtectedHelloConfig.class)
class ProtectedHelloTest {

    @Autowired
    private ProtectedHello hello;

    @Test
    @WithMockUser
    void withMockUser() {
        final String said = hello.say();
        assertEquals("Hello, user!", said);
    }

    @Test
    @WithMockUser(username = "hoge")
    void withMockUserUsername() {
        final String said = hello.say();
        assertEquals("Hello, hoge!", said);
    }
}
