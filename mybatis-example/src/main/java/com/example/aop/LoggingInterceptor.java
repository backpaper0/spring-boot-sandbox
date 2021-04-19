package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingInterceptor {

	@Around("@within(com.example.aop.MyAop)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Logger logger = LoggerFactory.getLogger(pjp.getSignature().getDeclaringType());
		try {
			logger.info("begin");
			return pjp.proceed();
		} finally {
			logger.info("end");
		}
	}
}
