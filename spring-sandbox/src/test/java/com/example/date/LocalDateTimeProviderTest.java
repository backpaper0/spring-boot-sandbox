package com.example.date;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@link LocalDateTimeProvider}のテスト。
 *
 */
public class LocalDateTimeProviderTest {

	/**
	 * 現在日時の提供をテストする。
	 *
	 */
	@SpringBootTest
	static class DefaultTest {

		@Autowired
		LocalDateTimeProvider sut;

		@Test
		void testNow() {
			LocalDateTime actual = sut.now();
			assertThat(actual).isBeforeOrEqualTo(LocalDateTime.now());
		}
	}

	/**
	 * 固定値の提供をテストする。
	 *
	 */
	@SpringBootTest(properties = "example.datetime.fixed-value=19990102030405")
	static class FixedValueTest {

		@Autowired
		LocalDateTimeProvider sut;

		@Test
		void testFixedValue() {
			LocalDateTime actual = sut.now();
			assertThat(actual).isEqualTo(LocalDateTime.parse("1999-01-02T03:04:05"));
		}
	}
}
