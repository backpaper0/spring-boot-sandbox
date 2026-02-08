package com.example.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ClassFilters;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class SimpleLoggingConfiguration {

    @Bean
    DefaultPointcutAdvisor simpleLoggingAdvisor() {
        var pointcut = new AnnotationMatchingPointcut(Service.class);
        var interceptor = new SimpleLoggingInterceptor();
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }

    static class SimpleLoggingInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            var logger = LoggerFactory.getLogger(invocation.getThis().getClass());
            var className = invocation.getThis().getClass().getSimpleName();
            var methodName = invocation.getMethod().getName();
            try {
                logger.info("begin - {}#{}", className, methodName);
                return invocation.proceed();
            } finally {
                logger.info("end - {}#{}", className, methodName);
            }
        }

    }
}
