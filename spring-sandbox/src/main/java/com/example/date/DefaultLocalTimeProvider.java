package com.example.date;

import java.time.LocalTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 現在時刻を提供するクラス。
 *
 */
@Component
//該当のプロパティがfalseに設定されている、または何も設定されていなければこのクラスが有効になる
@ConditionalOnProperty(value = "example.time.fixed-value", havingValue = "false", matchIfMissing = true)
public class DefaultLocalTimeProvider implements LocalTimeProvider {

	@Override
	public LocalTime now() {
		return LocalTime.now();
	}
}
