package com.example.core.jdbc;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

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
	public DataSource routingDataSource(
			@LeaderDataSource DataSource leaderDataSource,
			@FollowerDataSource DataSource followerDataSource) {
		RoutingDataSource dataSource = new RoutingDataSource();
		// データソースのタイプとデータソースを関連付ける
		dataSource.setTargetDataSources(Map.of(
				DataSourceType.LEADER, leaderDataSource,
				DataSourceType.FOLLOWER, followerDataSource));
		// RoutingDataSource自体をコンテナに登録せず、ライフサイクル管理は対象外になるため
		// 初期処理を手動で実行する
		dataSource.afterPropertiesSet();
		// TransactionSynchronizationManager.setCurrentTransactionReadOnlyの実行後に
		// determineCurrentLookupKeyが実行されるようにLazyConnectionDataSourceProxyでラップ
		return new LazyConnectionDataSourceProxy(dataSource);
	}
}
