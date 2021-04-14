package com.example.postconstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class PostConstructTest {

	@Autowired
	private PostConstructDemo sut;

	@Test
	void test() throws Exception {
		sut.run();
	}
}
