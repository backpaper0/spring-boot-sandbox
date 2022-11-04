package com.example.date;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * 固定された現在日付を提供するクラス。
 *
 */
@Component
//該当のプロパティが設定されていればこのクラスが有効になる
@ConditionalOnProperty("example.date.fixed-value")
@ConfigurationProperties(prefix = "example.date")
public class FixedLocalDateProvider implements LocalDateProvider {

	/**
	 * 固定された現在日付
	 */
	@DateTimeFormat(pattern = "uuuuMMdd")
	private LocalDate fixedValue;

	@Override
	public LocalDate now() {
		return fixedValue;
	}

	/**
	 * 固定された現在日付を設定する。
	 *
	 * @param fixedValue 固定された現在日付
	 */
	public void setFixedValue(LocalDate fixedValue) {
		this.fixedValue = fixedValue;
	}
}
