package com.example.example3;

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
class CustomValidationExampleControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void valid() throws Exception {
		mvc.perform(post("/3")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP")
				.param("text", "hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Valid"));
	}

	@Test
	void invalid_required() throws Exception {
		mvc.perform(post("/3")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP")
				.param("text", ""))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Required"));
	}

	@Test
	void invalid_size() throws Exception {
		mvc.perform(post("/3")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP")
				.param("text", "123456"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("0〜5にしてください"));
	}
}
