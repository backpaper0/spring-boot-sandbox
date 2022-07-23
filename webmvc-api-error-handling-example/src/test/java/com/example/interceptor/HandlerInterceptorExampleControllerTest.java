package com.example.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HandlerInterceptorExampleControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	void test() throws Exception {

		assertNull(ClassNameHolder.get());

		mvc.perform(get("/handler-interceptor-example"))
				.andExpect(content().string("HandlerInterceptorExampleController"));

		assertNull(ClassNameHolder.get());
	}
}
