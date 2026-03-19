package com.example.globalmethodsecurityexample;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class GlobalMethodSecurityExampleApplicationTests {

    MockMvc mvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(
            username = "xxx",
            roles = {"FOO", "BAR"})
    void fooBar() throws Exception {
        mvc.perform(get("/foo")).andExpect(status().isOk());
        mvc.perform(get("/bar")).andExpect(status().isOk());
        mvc.perform(get("/baz")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            username = "xxx",
            roles = {"BAR"})
    void bar() throws Exception {
        mvc.perform(get("/foo")).andExpect(status().isForbidden());
        mvc.perform(get("/bar")).andExpect(status().isOk());
        mvc.perform(get("/baz")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            username = "xxx",
            roles = {"BAZ"})
    void baz() throws Exception {
        mvc.perform(get("/foo")).andExpect(status().isForbidden());
        mvc.perform(get("/bar")).andExpect(status().isForbidden());
        mvc.perform(get("/baz")).andExpect(status().isOk());
    }
}
