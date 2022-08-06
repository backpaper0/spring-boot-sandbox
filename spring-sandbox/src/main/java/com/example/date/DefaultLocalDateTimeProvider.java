package com.example.date;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 現在日時を提供するクラス。
 *
 */
@Component
//該当のプロパティがfalseに設定されている、または何も設定されていなければこのクラスが有効になる
@ConditionalOnProperty(value = "example.datetime.fixed-value", havingValue = "false", matchIfMissing = true)
public class DefaultLocalDateTimeProvider implements LocalDateTimeProvider {

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}
}
