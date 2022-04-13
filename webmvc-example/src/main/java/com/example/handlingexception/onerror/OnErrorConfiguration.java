package com.example.handlingexception.onerror;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnErrorConfiguration {

	private final OnErrorInterceptor onErrorInterceptor;

	public OnErrorConfiguration(OnErrorInterceptor onErrorInterceptor) {
		this.onErrorInterceptor = onErrorInterceptor;
	}

	@Bean
	public DefaultPointcutAdvisor onErrorPointcutAdvisor() {
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		Pointcut pointcut = new ComposablePointcut(new AnnotationMethodMatcher(OnError.class));
		advisor.setPointcut(pointcut);
		advisor.setAdvice(onErrorInterceptor);
		return advisor;
	}
}
