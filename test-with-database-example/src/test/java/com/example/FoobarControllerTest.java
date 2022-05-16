package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class FoobarControllerTest {

	private MockMvc mockMvc;

	@BeforeEach
	void init(WebApplicationContext wac) {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@Transactional
	@Sql("foobar.sql")
	void test() throws Exception {
		mockMvc.perform(get("/foobar"))
				.andExpectAll(status().isOk(),
						jsonPath("$[0].id").isNumber(),
						jsonPath("$[0].content").value("foo"),
						jsonPath("$[1].id").isNumber(),
						jsonPath("$[1].content").value("bar"),
						jsonPath("$[2].id").isNumber(),
						jsonPath("$[2].content").value("baz"),
						jsonPath("$[3].id").isNumber(),
						jsonPath("$[3].content").value("qux"));
	}
}
