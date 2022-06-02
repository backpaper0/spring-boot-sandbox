package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ExampleControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	void valid() throws Exception {
		mvc.perform(post("/example")
				.param("msg", "123"))
				.andExpectAll(
						status().isOk(),
						jsonPath("$.msg").value("123"));
	}

	@Test
	void invalid() throws Exception {
		mvc.perform(post("/example")
				.param("msg", "1234"))
				.andExpectAll(
						status().isBadRequest(),
						jsonPath("$['post.msg']").exists());
	}
}
