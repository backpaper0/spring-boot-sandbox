package com.example.transaction;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.example.jdbc.RoutingDataSource;

public class CustomTransactionalInterceptor extends TransactionInterceptor {

    @Override
    protected Object invokeWithinTransaction(final Method method, final Class<?> targetClass,
            final InvocationCallback invocation) throws Throwable {
        final CustomTransactional ct = findAnnotation(method, targetClass);
        RoutingDataSource.setLookupKey(ct.value());
        try {
            return super.invokeWithinTransaction(method, targetClass, invocation);
        } finally {
            RoutingDataSource.removeLookupKey();
        }
    }

    private static CustomTransactional findAnnotation(final Method method,
            final Class<?> targetClass) {
        final CustomTransactional ct = AnnotationUtils.findAnnotation(method,
                CustomTransactional.class);
        if (ct != null) {
            return ct;
        }
        return AnnotationUtils.findAnnotation(targetClass, CustomTransactional.class);
    }
}
