package com.example.select;

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
public class SelectTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void test1() throws Exception {
        mvc.perform(post("/select").param("mySelect", "a"))
                .andExpectAll(model().attribute("selectForm", new SelectForm("a")));
    }

    @Test
    void test2() throws Exception {
        mvc.perform(post("/select")).andExpectAll(model().attribute("selectForm", new SelectForm(null)));
    }
}
