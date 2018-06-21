package com.example.example3;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomValidationExampleControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void valid() throws Exception {
        mvc.perform(post("/3")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", "hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid"));
    }

    @Test
    public void invalid_required() throws Exception {
        mvc.perform(post("/3")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Required"));
    }

    @Test
    public void invalid_size() throws Exception {
        mvc.perform(post("/3")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", "123456"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("size must be between 0 and 5"));
    }
}
