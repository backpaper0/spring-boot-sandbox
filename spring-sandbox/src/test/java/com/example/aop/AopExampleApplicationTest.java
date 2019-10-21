package com.example.aop;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopExampleApplicationTest {

    @Autowired
    private Hello hello;

    @Test
    void say() throws Exception {
        assertEquals("*hello*", hello.say());
    }
}
