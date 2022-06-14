package com.example.resttemplateexample;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.SmartValidator;
import org.springframework.web.client.RestOperations;

@Configuration
public class ValidationInterceptionConfig {

	private final SmartValidator validator;

	public ValidationInterceptionConfig(SmartValidator validator) {
		this.validator = validator;
	}

	@Bean
	public PointcutAdvisor pointcutAdvisor() {
		NameMatchMethodPointcutAdvisor pointcutAdvisor = new NameMatchMethodPointcutAdvisor();

		ClassFilter classFilter = new RootClassFilter(RestOperations.class);
		pointcutAdvisor.setClassFilter(classFilter);

		pointcutAdvisor.setMappedNames(
				"exchange",
				"getForEntity",
				"postForEntity",
				"execute",
				"getForObject",
				"patchForObject",
				"postForObject");

		pointcutAdvisor.setAdvice(new ValidationInterceptor(validator));

		return pointcutAdvisor;
	}
}
