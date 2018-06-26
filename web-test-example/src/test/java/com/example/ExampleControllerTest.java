package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class ExampleControllerTest {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = standaloneSetup(new ExampleController()).build();
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(get("/example"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void notFound() throws Exception {
        mvc.perform(get("/unknown"))
                .andExpect(status().isNotFound());
    }
}
