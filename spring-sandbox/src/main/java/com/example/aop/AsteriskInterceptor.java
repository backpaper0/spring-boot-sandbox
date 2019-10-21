package com.example.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AsteriskInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        if (String.class.isAssignableFrom(invocation.getMethod().getReturnType())) {
            return "*" + invocation.proceed() + "*";
        }
        return invocation.proceed();
    }
}
