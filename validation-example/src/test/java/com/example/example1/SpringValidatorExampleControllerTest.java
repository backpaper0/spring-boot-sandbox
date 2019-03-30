package com.example.example1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig
@SpringBootTest
class SpringValidatorExampleControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void valid() throws Exception {
        mvc.perform(post("/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", "hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid"));
    }

    @Test
    void invalid_null() throws Exception {
        mvc.perform(post("/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Required"));
    }

    @Test
    void invalid_empty() throws Exception {
        mvc.perform(post("/3")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Required"));
    }
}
