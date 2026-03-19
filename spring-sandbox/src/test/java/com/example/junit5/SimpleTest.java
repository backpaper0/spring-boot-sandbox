package com.example.junit5;

import static org.junit.jupiter.api.Assertions.*;

import com.example.junit5.hello.Hello;
import com.example.junit5.hello.HelloConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(HelloConfiguration.class)
class SimpleTest {

    @Autowired
    private Hello hello;

    @Test
    void say() throws Exception {
        assertEquals("Hello, world!", hello.say());
    }
}
