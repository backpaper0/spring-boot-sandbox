package com.example.core.jdbc;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

public class RoutingInteceptor implements MethodInterceptor {

	private final RoutingDataSource routingDataSource;
	private final TransactionAttributeSource tas;

	public RoutingInteceptor(RoutingDataSource routingDataSource, TransactionAttributeSource tas) {
		this.routingDataSource = routingDataSource;
		this.tas = tas;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (!routingDataSource.routeIsNull()) {
			return invocation.proceed();
		}
		Method method = invocation.getMethod();
		Class<?> targetClass = invocation.getThis().getClass();
		TransactionAttribute txAttr = tas.getTransactionAttribute(method, targetClass);
		try {
			if (txAttr.isReadOnly()) {
				routingDataSource.setRouting(Routing.FOLLOWER);
			} else {
				routingDataSource.setRouting(Routing.LEADER);
			}
			return invocation.proceed();
		} finally {
			routingDataSource.clearRouting();
		}
	}
}
