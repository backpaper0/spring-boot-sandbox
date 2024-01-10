package com.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.lang.Nullable;

@SpringBootApplication
public class App implements BeanPostProcessor {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	@Nullable
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof JdbcMappingContext c) {
			// ウィンドウ関数を利用して集約を1度のクエリーで取得する。
			// https://docs.spring.io/spring-data/relational/reference/jdbc/entity-persistence.html#jdbc.loading-aggregates
			c.setSingleQueryLoadingEnabled(true);
		}
		return bean;
	}
}
