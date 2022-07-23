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

import com.example.core.annotation.FollowerDataSource;
import com.example.core.annotation.LeaderDataSource;
import com.example.core.jdbc.Routing;
import com.example.core.jdbc.RoutingDataSource;
import com.example.core.jdbc.RoutingInteceptor;

@Configuration
public class RoutingDataSourceConfiguration {

	private final DataSource leaderDataSource;
	private final DataSource followerDataSource;
	private final TransactionAttributeSource tas;

	public RoutingDataSourceConfiguration(
			@LeaderDataSource DataSource leaderDataSource,
			@FollowerDataSource DataSource followerDataSource,
			TransactionAttributeSource tas) {
		this.leaderDataSource = leaderDataSource;
		this.followerDataSource = followerDataSource;
		this.tas = tas;
	}

	@Bean
	@Primary
	public RoutingDataSource routingDataSource() {
		RoutingDataSource dataSource = new RoutingDataSource();
		dataSource.setTargetDataSources(Map.of(
				Routing.LEADER, leaderDataSource,
				Routing.FOLLOWER, followerDataSource));
		return dataSource;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryTransactionAttributeSourceAdvisor routingDataSourcePointcutAdvisor() {
		BeanFactoryTransactionAttributeSourceAdvisor pointcutAdvisor = new BeanFactoryTransactionAttributeSourceAdvisor();
		pointcutAdvisor.setTransactionAttributeSource(tas);
		pointcutAdvisor.setAdvice(routingInteceptor());
		return pointcutAdvisor;
	}

	@Bean
	public RoutingInteceptor routingInteceptor() {
		return new RoutingInteceptor(routingDataSource(), tas);
	}
}
