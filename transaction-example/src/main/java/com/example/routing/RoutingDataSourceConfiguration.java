package com.example.routing;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.jdbc.datasource.lookup.BeanFactoryDataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

@Configuration
public class RoutingDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "foo.datasource")
	public DataSourceProperties fooDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource fooDataSource() {
		return fooDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "bar.datasource")
	public DataSourceProperties barDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource barDataSource() {
		return barDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	public DataSourceProperties fakeDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public DataSource routingDataSource() {
		final Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(LookupKey.FOO, "fooDataSource");
		targetDataSources.put(LookupKey.BAR, "barDataSource");
		final RoutingDataSource dataSource = new RoutingDataSource();
		dataSource.setDataSourceLookup(dataSourceLookup());
		dataSource.setTargetDataSources(targetDataSources);
		return dataSource;
	}

	@Bean
	public DataSourceLookup dataSourceLookup() {
		return new BeanFactoryDataSourceLookup();
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public PointcutAdvisor routingPointcutAdvisor() {
		final Pointcut pointcut = new ComposablePointcut(
				new AnnotationMatchingPointcut(null, Use.class, true))
						.union(new AnnotationMatchingPointcut(Use.class, null, true));
		final DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(new UseInterceptor());
		advisor.setPointcut(pointcut);
		return advisor;
	}
}
