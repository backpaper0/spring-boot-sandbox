package com.example.date;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@link LocalDateProvider}のテスト。
 *
 */
public class LocalDateProviderTest {

	/**
	 * 現在日付の提供をテストする。
	 *
	 */
	@SpringBootTest
	static class DefaultTest {

		@Autowired
		LocalDateProvider sut;

		@Test
		void testNow() {
			LocalDate actual = sut.now();
			assertThat(actual).isBeforeOrEqualTo(LocalDate.now());
		}
	}

	/**
	 * 固定値の提供をテストする。
	 *
	 */
	@SpringBootTest(properties = "example.date.fixed-value=19990102")
	static class FixedValueTest {

		@Autowired
		LocalDateProvider sut;

		@Test
		void testFixedValue() {
			LocalDate actual = sut.now();
			assertThat(actual).isEqualTo(LocalDate.parse("1999-01-02"));
		}
	}
}
