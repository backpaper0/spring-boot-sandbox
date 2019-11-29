package com.example.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsteriskConfiguration {

    @Bean
    public BeanPostProcessor autoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public Advisor loggingPointcutAdvisor() {
        final DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(new AsteriskInterceptor());
        pointcutAdvisor.setPointcut(AnnotationMatchingPointcut.forMethodAnnotation(Asterisk.class));
        return pointcutAdvisor;
    }
}
