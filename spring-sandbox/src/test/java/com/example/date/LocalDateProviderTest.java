package com.example.date;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	@SpringBootTest(properties = {
			"example.datetime.fixed-value=20220805120000",
			"example.date.fixed-value=20220805",
			"example.time.fixed-value=120000"
	})
	static class FixedValueTest {

		@Autowired
		LocalDateTimeProvider sut1;
		@Autowired
		LocalDateProvider sut;
		@Autowired
		LocalTimeProvider sut3;

		@Test
		void testFixedValue() {
			LocalDate actual = sut.now();
			assertThat(actual).isEqualTo(LocalDate.parse("2022-08-05"));
			assertThat(sut1.now()).isEqualTo(LocalDateTime.parse("2022-08-05T12:00:00"));
			assertThat(sut3.now()).isEqualTo(LocalTime.parse("12:00:00"));
		}
	}
}
