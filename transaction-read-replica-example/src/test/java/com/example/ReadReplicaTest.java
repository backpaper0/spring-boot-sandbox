package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.model.Example;
import com.example.service.ExampleService;

@SpringBootTest
public class ReadReplicaTest {

	@Autowired
	private ExampleService service;

	@Test
	void primary() {
		List<Example> examples = service.findAll();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	@Test
	void readReplica() {
		List<Example> examples = service.findAllFromReadReplica();
		assertEquals(List.of(new Example(3, "baz"), new Example(4, "qux")), examples);
	}

	@Test
	void readReplica2() {
		List<Example> examples = service.findAllFromReadReplica2();
		assertEquals(List.of(new Example(3, "baz"), new Example(4, "qux")), examples);
	}
}
