package com.example.transaction;

import java.lang.reflect.AnnotatedElement;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

public class CustomTransactionalParser implements TransactionAnnotationParser {

	@Override
	public TransactionAttribute parseTransactionAnnotation(final AnnotatedElement ae) {
		final CustomTransactional ct = AnnotationUtils.findAnnotation(ae,
				CustomTransactional.class);
		if (ct != null) {
			return new DefaultTransactionAttribute(ct.propagation().value());
		}
		return null;
	}
}
