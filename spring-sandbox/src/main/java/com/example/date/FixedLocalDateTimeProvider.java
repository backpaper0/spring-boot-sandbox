package com.example.date;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * 固定された現在日時を提供するクラス。
 *
 */
@Component
//該当のプロパティが設定されていればこのクラスが有効になる
@ConditionalOnProperty("example.datetime.fixed-value")
@ConfigurationProperties(prefix = "example.datetime")
public class FixedLocalDateTimeProvider implements LocalDateTimeProvider {

	/**
	 * 固定された現在日時
	 */
	@DateTimeFormat(pattern = "uuuuMMddHHmmss")
	private LocalDateTime fixedValue;

	@Override
	public LocalDateTime now() {
		return fixedValue;
	}

	/**
	 * 固定された現在日時を設定する。
	 *
	 * @param fixedValue 固定された現在日時
	 */
	public void setFixedValue(LocalDateTime fixedValue) {
		this.fixedValue = fixedValue;
	}
}
