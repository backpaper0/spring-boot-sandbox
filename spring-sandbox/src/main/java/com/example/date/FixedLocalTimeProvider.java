package com.example.date;

import java.time.LocalTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * 固定された現在時刻を提供するクラス。
 *
 */
@Component
// 該当のプロパティが設定されていればこのクラスが有効になる
@ConditionalOnProperty("example.time.fixed-value")
@ConfigurationProperties(prefix = "example.time")
public class FixedLocalTimeProvider implements LocalTimeProvider {

	/**
	 * 固定された現在時刻
	 */
	@DateTimeFormat(pattern = "HHmmss")
	private LocalTime fixedValue;

	@Override
	public LocalTime now() {
		return fixedValue;
	}

	/**
	 * 固定された現在時刻を設定する。
	 *
	 * @param fixedValue 固定された現在時刻
	 */
	public void setFixedValue(LocalTime fixedValue) {
		this.fixedValue = fixedValue;
	}
}
