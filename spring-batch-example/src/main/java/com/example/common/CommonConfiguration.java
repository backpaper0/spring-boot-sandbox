package com.example.common;

import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class CommonConfiguration {

	private final LocalValidatorFactoryBean localValidatorFactoryBean;

	public CommonConfiguration(LocalValidatorFactoryBean localValidatorFactoryBean) {
		this.localValidatorFactoryBean = localValidatorFactoryBean;
	}

	@Bean
	public BeanValidatingItemProcessor<Object> beanValidatingItemProcessor() {
		return new BeanValidatingItemProcessor<>(localValidatorFactoryBean);
	}
}
