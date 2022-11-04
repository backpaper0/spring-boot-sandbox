package com.example.code;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

public class ExtendedBeanPropertySqlParameterSource extends BeanPropertySqlParameterSource {

	public ExtendedBeanPropertySqlParameterSource(final Object object) {
		super(object);
	}

	@Override
	public Object getValue(final String paramName) throws IllegalArgumentException {
		final Object value = super.getValue(paramName);
		if (value instanceof Code) {
			return ((Code) value).getCode();
		}
		return value;
	}
}
