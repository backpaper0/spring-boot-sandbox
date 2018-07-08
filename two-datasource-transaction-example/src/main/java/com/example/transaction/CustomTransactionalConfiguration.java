package com.example.transaction;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

@Configuration
public class CustomTransactionalConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor customTransactionalPointcutAdvisor() {
        final BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
        advisor.setTransactionAttributeSource(customTransactionalTransactionAttributeSource());
        advisor.setAdvice(customTransactionalInterceptor());
        return advisor;
    }

    @Bean
    public TransactionAttributeSource customTransactionalTransactionAttributeSource() {
        final CustomTransactionalParser annotationParser = new CustomTransactionalParser();
        return new AnnotationTransactionAttributeSource(annotationParser);
    }

    @Bean
    public CustomTransactionalInterceptor customTransactionalInterceptor() {
        final CustomTransactionalInterceptor interceptor = new CustomTransactionalInterceptor();
        interceptor.setTransactionAttributeSource(
                customTransactionalTransactionAttributeSource());
        return interceptor;
    }
}
