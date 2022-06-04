package com.example.parameter;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class MyParameterSupplierImpl implements MyParameterSupplier {

	@Value("#{jobParameters['my.param']}")
	private String value;

	@Override
	public String get() {
		return value;
	}
}
