package com.example.jdbc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("first")
    public DataSource firstDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Qualifier("second")
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("second")
    public DataSource secondDataSource() {
        return secondDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        final Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSources.FIRST, firstDataSource());
        targetDataSources.put(DataSources.SECOND, secondDataSource());
        final RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        return dataSource;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor routingDataSourcePointcutAdvisor() {
        final DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(new UseDataSourceInterceptor());
        advisor.setPointcut(new AnnotationMatchingPointcut(null, UseDataSource.class, true));
        return advisor;
    }

    private static class UseDataSourceInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            final Method method = invocation.getMethod();
            final UseDataSource useDataSource = AnnotationUtils.findAnnotation(method,
                    UseDataSource.class);
            RoutingDataSource.setLookupKey(useDataSource.value());
            try {
                return invocation.proceed();
            } finally {
                RoutingDataSource.removeLookupKey();
            }
        }
    }
}
