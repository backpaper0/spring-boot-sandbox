package com.example.handlingexception.exception;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

public class BusinessException extends RuntimeException {

	private final String code;
	private final String field;

	public BusinessException(String code, String field) {
		this.code = code;
		this.field = field;
	}

	public BusinessException(String code) {
		this.code = code;
		this.field = null;
	}

	public void rejectTo(Errors errors, MessageSource messageSource) {
		if (field != null) {
			errors.rejectValue(field, code, new Object[] {
					messageSource.getMessage(field, new Object[0], null)
			}, null);
		} else {
			errors.reject(code);
		}
	}
}
