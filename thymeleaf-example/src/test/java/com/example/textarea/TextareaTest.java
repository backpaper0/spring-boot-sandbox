package com.example.textarea;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TextareaTest {

	@Autowired
	MockMvc mvc;

	@Test
	void test1() throws Exception {
		mvc.perform(post("/textarea").param("myTextarea", "foo\r\nbar"))
				.andExpectAll(
						model().attribute("textareaForm", new TextareaForm("foo\r\nbar")));
	}

	@Test
	void test2() throws Exception {
		mvc.perform(post("/textarea"))
				.andExpectAll(
						model().attribute("textareaForm", new TextareaForm(null)));
	}

}