package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CharReplacementTest {

	@Autowired
	CharReplacementProcessor sut;

	@Test
	void process() {
		String actual = sut.process("🍣食べたい");
		String expected = "寿食べたい";
		assertEquals(expected, actual);
	}
}
