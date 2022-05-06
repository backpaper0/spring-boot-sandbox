package com.example.parameter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParameterTest {

	@Autowired
	ParameterMapper mapper;

	@Test
	void testSelect1Param() {
		String actual = mapper.select1Param(1);
		assertEquals("foo", actual);
	}

	@Test
	void testSelect2ParamsWithName() {
		List<String> actual = mapper.select2ParamsWithName(1, 2);
		assertEquals(List.of("foo", "bar"), actual);
	}

	@Test
	void testSelect2ParamsWithIndex() {
		List<String> actual = mapper.select2ParamsWithIndex(2, 3);
		assertEquals(List.of("bar", "baz"), actual);
	}
}
