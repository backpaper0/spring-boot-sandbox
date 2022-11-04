package com.example.date;

import java.time.LocalTime;

/**
 * 時刻を提供するインターフェース。
 *
 */
public interface LocalTimeProvider {

	/**
	 * 時刻を提供する。
	 *
	 * @return 時刻
	 */
	LocalTime now();
}
