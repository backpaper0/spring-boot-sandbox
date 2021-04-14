package com.example.postconstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AsteriskInterceptor {

	@Around("@within(com.example.Asterisk)")
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		Logger logger = LoggerFactory.getLogger(pjp.getSignature().getDeclaringType());
		logger.info("Aspect: {}#{}", pjp.getSignature().getDeclaringType().getSimpleName(),
				pjp.getSignature().getName());
		return pjp.proceed();
	}
}
