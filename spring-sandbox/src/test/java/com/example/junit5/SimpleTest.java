package com.example.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.junit5.hello.Hello;
import com.example.junit5.hello.HelloConfiguration;

@SpringJUnitConfig(HelloConfiguration.class)
public class SimpleTest {

    @Autowired
    private Hello hello;

    @Test
    void say() throws Exception {
        assertEquals("Hello, world!", hello.say());
    }
}
