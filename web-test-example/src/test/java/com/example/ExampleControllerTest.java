package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

class ExampleControllerTest {

    private MockMvc mvc;

    @BeforeEach
    void setUp() throws Exception {
        mvc = standaloneSetup(new ExampleController()).build();
    }

    @Test
    void getHello() throws Exception {
        mvc.perform(get("/example"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    void notFound() throws Exception {
        mvc.perform(get("/unknown"))
                .andExpect(status().isNotFound());
    }
}
