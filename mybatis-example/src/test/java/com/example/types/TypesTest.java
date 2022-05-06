package com.example.types;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TypesTest {

	@Autowired
	TypesMapper mapper;

	@Test
	void test() {
		Model4 actual = mapper.selectById(1);

		Model4 expected = new Model4(
				1, 2L, new BigDecimal("3"), "foobar", true, true,
				LocalDateTime.parse("2022-05-05T23:30:00"),
				LocalDate.parse("2022-05-05"));

		assertEquals(expected, actual);
	}
}
