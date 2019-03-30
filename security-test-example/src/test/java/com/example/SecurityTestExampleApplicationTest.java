package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
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
class SecurityTestExampleApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void foo() throws Exception {
        mvc.perform(get("/foo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(":foo"));
    }

    @Test
    @WithMockUser(username = "hoge")
    void foo_with_login() throws Exception {
        mvc.perform(get("/foo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("hoge:foo"));
    }

    @Test
    @WithMockUser(username = "hoge", roles = "BAR")
    void bar() throws Exception {
        mvc.perform(get("/bar"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("hoge:bar"));
    }

    @Test
    @WithMockUser(username = "hoge", roles = "FOO")
    void bar_NG() throws Exception {
        mvc.perform(get("/bar"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void bar_without_annotation() throws Exception {
        mvc.perform(get("/bar").with(user("fuga").roles("BAR")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("fuga:bar"));
    }
}
