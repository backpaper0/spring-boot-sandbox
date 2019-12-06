package com.example.example2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class BeanValidationExampleControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void valid() throws Exception {
        mvc.perform(post("/2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP")
                .param("text", "hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid"));
    }

    @Test
    void invalid_null() throws Exception {
        mvc.perform(post("/2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("必須です"));
    }

    @Test
    void invalid_empty() throws Exception {
        mvc.perform(post("/2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP")
                .param("text", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("size must be between 1 and 2147483647"));
    }
}
