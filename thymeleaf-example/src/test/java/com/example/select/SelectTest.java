package com.example.select;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SelectTest {

	@Autowired
	MockMvc mvc;

	@Test
	void test1() throws Exception {
		mvc.perform(post("/select").param("mySelect", "a"))
				.andExpectAll(
						model().attribute("selectForm", new SelectForm("a")));
	}

	@Test
	void test2() throws Exception {
		mvc.perform(post("/select"))
				.andExpectAll(
						model().attribute("selectForm", new SelectForm(null)));
	}

}