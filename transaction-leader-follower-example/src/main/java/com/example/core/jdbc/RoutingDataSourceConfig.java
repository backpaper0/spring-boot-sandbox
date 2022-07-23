package com.example.core.jdbc;

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

/**
 * ルーティングデータソースに関係する設定。
 *
 */
@Configuration
public class RoutingDataSourceConfig {

	/**
	 * ルーティングデータソースを構築する。
	 * 
	 * @param leaderDataSource リーダーデータソース
	 * @param followerDataSource フォロワーデータソース
	 * @return ルーティングデータソース
	 */
	@Bean
	// データソースをインジェクションする場面ではこのルーディングデータソースを
	// インジェクションしたいためPrimaryアノテーションを付けている。
	@Primary
	public RoutingDataSource routingDataSource(
			@LeaderDataSource DataSource leaderDataSource,
			@FollowerDataSource DataSource followerDataSource) {
		RoutingDataSource dataSource = new RoutingDataSource();
		// データソースのタイプとデータソースを関連付ける
		dataSource.setTargetDataSources(Map.of(
				DataSourceType.LEADER, leaderDataSource,
				DataSourceType.FOLLOWER, followerDataSource));
		return dataSource;
	}

	/**
	 * ルーティングインターセプターを適用するAdvisorを構築する。
	 * 
	 * @param tas トランザクションの属性を取得するためのインターフェース
	 * @param routingInteceptor ルーティングインターセプター
	 * @return ルーティングインターセプターを適用するAdvisor
	 */
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryTransactionAttributeSourceAdvisor routingDataSourcePointcutAdvisor(
			TransactionAttributeSource tas,
			RoutingInteceptor routingInteceptor) {
		BeanFactoryTransactionAttributeSourceAdvisor pointcutAdvisor = new BeanFactoryTransactionAttributeSourceAdvisor();
		pointcutAdvisor.setTransactionAttributeSource(tas);
		pointcutAdvisor.setAdvice(routingInteceptor);
		return pointcutAdvisor;
	}
}
