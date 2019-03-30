package com.example.globalmethodsecurityexample;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig
@SpringBootTest
class GlobalMethodSecurityExampleApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "xxx", roles = { "FOO", "BAR" })
    void fooBar() throws Exception {
        mvc.perform(get("/foo")).andExpect(status().isOk());
        mvc.perform(get("/bar")).andExpect(status().isOk());
        mvc.perform(get("/baz")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "xxx", roles = { "BAR" })
    void bar() throws Exception {
        mvc.perform(get("/foo")).andExpect(status().isForbidden());
        mvc.perform(get("/bar")).andExpect(status().isOk());
        mvc.perform(get("/baz")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "xxx", roles = { "BAZ" })
    void baz() throws Exception {
        mvc.perform(get("/foo")).andExpect(status().isForbidden());
        mvc.perform(get("/bar")).andExpect(status().isForbidden());
        mvc.perform(get("/baz")).andExpect(status().isOk());
    }
}
