package com.example.routing;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

public class UseInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Use use = AnnotationUtils.findAnnotation(invocation.getMethod(), Use.class);
		if (use == null) {
			use = AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Use.class);
		}
		if (use == null) {
			return invocation.proceed();
		}
		final LookupKey current = RoutingDataSource.set(use.value());
		try {
			return invocation.proceed();
		} finally {
			RoutingDataSource.set(current);
		}
	}
}
