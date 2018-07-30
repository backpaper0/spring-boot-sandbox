package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.hello.Hello;
import com.example.hello.HelloConfiguration;
import com.example.hello.impl.MessageFormatter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HelloConfiguration.class)
public class MockTest {

    @Autowired
    private Hello hello;

    @MockBean
    private MessageFormatter messageFormatter;

    @Test
    void say() throws Exception {
        given(messageFormatter.format("Hello, %s!", "world")).willReturn("foobar");
        assertEquals("foobar", hello.say());
    }
}
