package com.example.aop;

import java.lang.reflect.Method;

import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.MethodMatchers;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Asterisk;

@Configuration
public class Config {

	@Bean
	public Advisor pointcutAdvisor() {
		DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();

		Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(Asterisk.class);
		ClassFilter classFilter = pointcut.getClassFilter();
		MethodMatcher methodMatcher = MethodMatchers.intersection(pointcut.getMethodMatcher(),
				new StaticMethodMatcher() {
					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						return method.getReturnType() == String.class;
					}
				});
		pointcutAdvisor.setPointcut(new ComposablePointcut(classFilter, methodMatcher));

		pointcutAdvisor.setAdvice(new AsteriskInterceptor());

		return pointcutAdvisor;
	}
}
