package com.example;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTestExampleApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void foo() throws Exception {
        mvc.perform(get("/foo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(":foo"));
    }

    @Test
    @WithMockUser(username = "hoge")
    public void foo_with_login() throws Exception {
        mvc.perform(get("/foo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("hoge:foo"));
    }

    @Test
    @WithMockUser(username = "hoge", roles = "BAR")
    public void bar() throws Exception {
        mvc.perform(get("/bar"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("hoge:bar"));
    }

    @Test
    @WithMockUser(username = "hoge", roles = "FOO")
    public void bar_NG() throws Exception {
        mvc.perform(get("/bar"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void bar_without_annotation() throws Exception {
        mvc.perform(get("/bar").with(user("fuga").roles("BAR")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("fuga:bar"));
    }
}
