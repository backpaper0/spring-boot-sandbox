package com.example.example4;

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
class MessageExampleControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void invalid() throws Exception {
		mvc.perform(post("/4")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header(HttpHeaders.ACCEPT_LANGUAGE, "ja-JP")
				.param("text", "1234")
				.param("num", "text")
				.param("vo", "abcd"))
				//                .andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0]").value("num item must be int"))
				.andExpect(jsonPath("$[1]")
						.value("value object must be text that size is between 1 and 3"))
				.andExpect(jsonPath("$[2]").value("テキスト項目 size must be between 1 and 3"));
	}
}
