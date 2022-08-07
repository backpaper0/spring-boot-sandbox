package com.example.numbering;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 現在日付を提供するクラス。
 *
 */
@Component
//該当のプロパティがfalseに設定されている、または何も設定されていなければこのクラスが有効になる
@ConditionalOnProperty(value = "example.date.fixed-value", havingValue = "false", matchIfMissing = true)
public class DefaultLocalDateProvider implements LocalDateProvider {

	@Override
	public LocalDate now() {
		return LocalDate.now();
	}
}
