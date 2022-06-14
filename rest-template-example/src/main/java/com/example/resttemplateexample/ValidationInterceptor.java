package com.example.resttemplateexample;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

public class ValidationInterceptor implements MethodInterceptor {

	private final SmartValidator validator;

	public ValidationInterceptor(SmartValidator validator) {
		this.validator = validator;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		assert RestOperations.class.isAssignableFrom(invocation.getThis().getClass());

		Object returnValue = invocation.proceed();
		Method method = invocation.getMethod();
		switch (method.getName()) {
		case "exchange":
		case "getForEntity":
		case "postForEntity":
			ResponseEntity<?> responseEntity = (ResponseEntity<?>) returnValue;
			validate(responseEntity.getBody());
			return returnValue;

		case "execute":
		case "getForObject":
		case "patchForObject":
		case "postForObject":
			validate(returnValue);
			return returnValue;

		default:
			return returnValue;
		}
	}

	private void validate(Object target) {
		BindingResult bindingResult = new BeanPropertyBindingResult(target, "target");
		validator.validate(target, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new RestClientException("Validation error", new BindException(bindingResult));
		}
	}
}
