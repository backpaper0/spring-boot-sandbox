package com.example.core.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import com.example.core.annotation.PrimaryDataSource;
import com.example.core.annotation.ReadReplicaDataSource;
import com.example.core.jdbc.RoutingDataSource;
import com.example.core.jdbc.RoutingInteceptor;

@Configuration(proxyBeanMethods = false)
public class RoutingDataSourceConfiguration {

	private final DataSource primaryDataSource;
	private final DataSource readReplicaDataSource;
	private final TransactionAttributeSource tas;

	public RoutingDataSourceConfiguration(
			@PrimaryDataSource DataSource primaryDataSource,
			@ReadReplicaDataSource DataSource readReplicaDataSource,
			TransactionAttributeSource tas) {
		this.primaryDataSource = primaryDataSource;
		this.readReplicaDataSource = readReplicaDataSource;
		this.tas = tas;
	}

	@Bean
	@Primary
	public RoutingDataSource routingDataSource() {
		RoutingDataSource dataSource = new RoutingDataSource();
		dataSource.setTargetDataSources(Map.of(
				Boolean.FALSE, primaryDataSource,
				Boolean.TRUE, readReplicaDataSource));
		return dataSource;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryTransactionAttributeSourceAdvisor routingDataSourcePointcutAdvisor(
			RoutingInteceptor inteceptor) {
		BeanFactoryTransactionAttributeSourceAdvisor pointcutAdvisor = new BeanFactoryTransactionAttributeSourceAdvisor();
		pointcutAdvisor.setTransactionAttributeSource(tas);
		pointcutAdvisor.setAdvice(inteceptor);
		return pointcutAdvisor;
	}

	@Bean
	public RoutingInteceptor routingInteceptor(RoutingDataSource routingDataSource) {
		return new RoutingInteceptor(routingDataSource, tas);
	}
}
