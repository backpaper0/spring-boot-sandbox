package com.example.component.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class BazInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (invocation.getMethod().getDeclaringClass() != Object.class
				&& invocation.getMethod().getReturnType() == String.class) {
			return "*" + invocation.proceed() + "*";
		}
		return invocation.proceed();
	}
}
