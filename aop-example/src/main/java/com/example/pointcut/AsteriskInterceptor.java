package com.example.pointcut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AsteriskInterceptor {

	@Around("@within(com.example.Asterisk) && execution(String *(..))")
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		return "*" + pjp.proceed() + "*";
	}
}
