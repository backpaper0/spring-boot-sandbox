package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.model.Example;
import com.example.service.Example1Service;

@SpringBootTest
public class LeaderFollowerTest {

	@Autowired
	private Example1Service service;

	@Test
	void findAllFromLeader1() {
		List<Example> examples = service.findAllFromLeader1();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	@Test
	void findAllFromFollower1() {
		List<Example> examples = service.findAllFromFollower1();
		assertEquals(List.of(new Example(3, "baz"), new Example(4, "qux")), examples);
	}

	@Test
	void findAllFromFollower2() {
		List<Example> examples = service.findAllFromFollower2();
		assertEquals(List.of(new Example(3, "baz"), new Example(4, "qux")), examples);
	}

	@Test
	void nestFindAllFromLeader1() {
		List<Example> examples = service.nestFindAllFromLeader1();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	@Test
	void nestFindAllFromLeader2() {
		List<Example> examples = service.nestFindAllFromLeader2();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	@Test
	void nestFindAllFromLeader3() {
		List<Example> examples = service.nestFindAllFromLeader3();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}
}
