package com.example.checkbox;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckboxTest {

	@Autowired
	MockMvc mvc;

	@Test
	void test1() throws Exception {
		mvc.perform(post("/checkbox").param("singleCheckbox", "on").param("multiCheckboxes", "foo", "bar"))
				.andExpectAll(
						model().attribute("checkboxForm", new CheckboxForm(true, new String[] { "foo", "bar" })));
	}

	@Test
	void test2() throws Exception {
		mvc.perform(post("/checkbox"))
				.andExpectAll(
						model().attribute("checkboxForm", new CheckboxForm(null, null)));
	}

}