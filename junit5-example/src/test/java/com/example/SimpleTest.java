package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HelloConfiguration.class)
public class SimpleTest {

    @Autowired
    private Hello hello;

    @Test
    void say() throws Exception {
        assertEquals("Hello, world!", hello.say());
    }
}
