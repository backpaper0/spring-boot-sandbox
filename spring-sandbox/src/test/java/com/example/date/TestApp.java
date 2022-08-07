package com.example.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApp {

	/** システム日時を取得するインターフェース */
	@Autowired
	private LocalDateTimeProvider localDateTimeProvider;

	/** システム日付を取得するインターフェース */
	@Autowired
	private LocalDateProvider localDateProvider;

	/** システム時刻を取得するインターフェース */
	@Autowired
	private LocalTimeProvider localTimeProvider;

	private void run() {
		// システム日時の取得
		LocalDateTime dateTime = localDateTimeProvider.now();

		// システム日付の取得
		LocalDate date = localDateProvider.now();

		// システム時刻の取得
		LocalTime time = localTimeProvider.now();
	}
}
