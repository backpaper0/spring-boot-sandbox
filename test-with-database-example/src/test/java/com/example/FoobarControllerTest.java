package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class FoobarControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	@Transactional
	@Sql("foobar.sql")
	void test() throws Exception {
		mvc.perform(get("/foobar"))
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
