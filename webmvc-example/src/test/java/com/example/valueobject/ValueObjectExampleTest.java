package com.example.valueobject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class ValueObjectExampleTest {

	private MockMvc mockMvc;

	@BeforeEach
	void init(WebApplicationContext wac) {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	void test() throws Exception {
		mockMvc.perform(post("/valueobjects")
				.param("id", "123")
				.param("content", "hello")
				.param("status", "TODO"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
						{
							"id": {"value": 123},
							"content": {"value": "hello"},
							"status": "TODO"
						}
						"""));
	}

	@Test
	void invalid() throws Exception {
		mockMvc.perform(post("/valueobjects")
				.param("id", "xxx")
				.param("content", "hello")
				.param("status", "TODO"))
				.andExpect(status().isOk())
				.andExpect(content().json("""
						{
							"fieldErrors": {
								"id": ["タスクIDは数字でなければいけません。"]
							},
							"globalErrors": []
						}
						"""));
	}
}
