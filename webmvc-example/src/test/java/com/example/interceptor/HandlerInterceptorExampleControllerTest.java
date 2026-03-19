package com.example.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class HandlerInterceptorExampleControllerTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void test() throws Exception {

        assertNull(ClassNameHolder.get());

        mvc.perform(get("/handler-interceptor-example"))
                .andExpect(content().string("HandlerInterceptorExampleController"));

        assertNull(ClassNameHolder.get());
    }
}
