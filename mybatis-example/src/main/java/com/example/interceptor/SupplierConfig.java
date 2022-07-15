package com.example.interceptor;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplierConfig {

	@Bean
	@ConditionalOnMissingBean(TableMetadataAutoSetInterceptor.IdSupplier.class)
	public TableMetadataAutoSetInterceptor.IdSupplier defaultIdSupplier() {
		return () -> "id";
	}

	@Bean
	@ConditionalOnMissingBean(TableMetadataAutoSetInterceptor.LocalDateTimeSupplier.class)
	public TableMetadataAutoSetInterceptor.LocalDateTimeSupplier defaultLocalDateTimeSupplier() {
		return LocalDateTime::now;
	}
}
