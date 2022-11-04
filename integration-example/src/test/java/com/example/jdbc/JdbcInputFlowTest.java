package com.example.jdbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = JdbcInputFlow.class)
class JdbcInputFlowTest {

	@Autowired
	private QueueChannel output;
	@Autowired
	private DataSource dataSource;

	@Test
	void test() {

		final List<MyMessage> list = (List<MyMessage>) output.receive()
				.getPayload();

		final Iterator<MyMessage> it = list.iterator();

		assertEquals(new MyMessage(1L, "foo", false), it.next());
		assertEquals(new MyMessage(2L, "bar", false), it.next());
		assertEquals(new MyMessage(3L, "baz", false), it.next());
		assertFalse(it.hasNext());

		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		final Long count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM messages WHERE sent = 0",
				Long.class);
		assertEquals((Long) 0L, count);
	}
}
