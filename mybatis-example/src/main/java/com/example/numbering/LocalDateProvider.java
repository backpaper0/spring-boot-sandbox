package com.example.numbering;

import java.time.LocalDate;

/**
 * 日付を提供するインターフェース。
 *
 */
public interface LocalDateProvider {

	/**
	 * 日付を返す。
	 *
	 * @return 日付
	 */
	LocalDate now();
}
