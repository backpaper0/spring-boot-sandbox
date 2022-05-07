package com.example.component.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BazConfig {

	private final BazInterceptor interceptor;

	public BazConfig(BazInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Bean
	public DefaultPointcutAdvisor bazPointcutAdvisor() {
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		Pointcut pointcut = AnnotationMatchingPointcut.forClassAnnotation(BazAop.class);
		advisor.setPointcut(pointcut);
		advisor.setAdvice(interceptor);
		return advisor;
	}
}
