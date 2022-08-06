package com.example.date;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@link LocalTimeProvider}のテスト。
 *
 */
public class LocalTimeProviderTest {

	/**
	 * 現在時刻の提供をテストする。
	 *
	 */
	@SpringBootTest
	static class DefaultTest {

		@Autowired
		LocalTimeProvider sut;

		@Test
		void testNow() {
			LocalTime actual = sut.now();
			assertThat(actual).isBeforeOrEqualTo(LocalTime.now());
		}
	}

	/**
	 * 固定値の提供をテストする。
	 *
	 */
	@SpringBootTest(properties = "example.time.fixed-value=030405")
	static class FixedValueTest {

		@Autowired
		LocalTimeProvider sut;

		@Test
		void testFixedValue() {
			LocalTime actual = sut.now();
			assertThat(actual).isEqualTo(LocalTime.parse("03:04:05"));
		}
	}
}
