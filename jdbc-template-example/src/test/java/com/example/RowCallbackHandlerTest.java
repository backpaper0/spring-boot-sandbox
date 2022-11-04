package com.example;

import java.sql.ResultSetMetaData;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

@SpringBootTest
class RowCallbackHandlerTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void test() throws Exception {
		final RowCallbackHandler handler = rs -> {
			final ResultSetMetaData meta = rs.getMetaData();
			final int columnCount = meta.getColumnCount();
			do {
				final Object[] os = new Object[columnCount];
				for (int i = 0; i < columnCount; i++) {
					final Object o = rs.getObject(i + 1);
					os[i] = o;
				}
				System.out.println(Arrays.toString(os));
			} while (rs.next());
		};
		jdbcTemplate.query("SELECT * FROM example ORDER BY id ASC", handler);
	}
}
