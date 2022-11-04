package com.example.date;

import java.time.LocalDateTime;

/**
 * 日時を提供するインターフェース。
 *
 */
public interface LocalDateTimeProvider {

	/**
	 * 日時を提供する。
	 *
	 * @return 日時
	 */
	LocalDateTime now();
}
