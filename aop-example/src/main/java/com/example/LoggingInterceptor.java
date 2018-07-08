package com.example;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInterceptor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        logger.info("*** begin {} ***", invocation.getMethod());
        try {
            return invocation.proceed();
        } finally {
            logger.info("*** end {} ***", invocation.getMethod());
        }
    }
}
