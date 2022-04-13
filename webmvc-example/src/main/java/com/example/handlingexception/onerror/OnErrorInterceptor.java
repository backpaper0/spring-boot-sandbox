package com.example.handlingexception.onerror;

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.example.handlingexception.exception.BusinessException;

@Component
public class OnErrorInterceptor implements MethodInterceptor {

	private final MessageSource messageSource;

	public OnErrorInterceptor(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		} catch (BusinessException exception) {
			Errors errors = Arrays.stream(invocation.getArguments())
					.filter(Errors.class::isInstance)
					.map(Errors.class::cast)
					.findAny()
					.orElseThrow(() -> exception);
			exception.rejectTo(errors, messageSource);
			OnError onError = AnnotationUtils.findAnnotation(invocation.getMethod(), OnError.class);
			return onError.value();
		}
	}
}
