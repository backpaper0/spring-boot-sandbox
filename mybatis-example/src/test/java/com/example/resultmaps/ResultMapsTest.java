package com.example.resultmaps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResultMapsTest {

	@Autowired
	ResultMapsMapper mapper;

	@Test
	void test() {
		List<Model1> actual = mapper.selectAll();

		List<Model1> expected = List.of(
				new Model1(1, "foo1", new Model2(1, "foo2"),
						List.of(new Model3(1, "foo3a"), new Model3(2, "foo3b"))),
				new Model1(2, "bar1", new Model2(2, "bar2"),
						List.of(new Model3(3, "bar3a"), new Model3(4, "bar3b"))));

		assertEquals(expected, actual);
	}
}
