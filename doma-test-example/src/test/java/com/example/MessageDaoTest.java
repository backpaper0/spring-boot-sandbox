package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageDaoTest {

	@Autowired
	private MessageDao dao;

	@Test
	@Order(2)
	void insert() {
		assertEquals(
				Collections.singletonList(new Message(1L, "Hello World")),
				dao.selectAll());

		final Result<Message> result = dao.insert(new Message("foobar"));

		assertEquals(1, result.getCount());
		assertEquals(
				Arrays.asList(new Message(1L, "Hello World"), new Message(2L, "foobar")),
				dao.selectAll());
	}

	@Test
	@Order(1)
	void selectAll() {
		assertEquals(
				Collections.singletonList(new Message(1L, "Hello World")),
				dao.selectAll());
	}
}
