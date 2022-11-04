package com.example.core.jdbc;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.example.core.annotation.FollowerDataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * フォロワーデータベースの設定。
 *
 */
@Configuration
public class FollowerConfig {

	/**
	 * フォロワーデータベースのプロパティを構築する。
	 *
	 * @return フォロワーデータベースのプロパティ
	 */
	@Bean
	@ConfigurationProperties(prefix = "follower.datasource")
	@FollowerDataSource
	public DataSourceProperties followerDataSourceProperties() {
		return new DataSourceProperties();
	}

	/**
	 * フォロワーデータベースのデータソースを構築する。
	 *
	 * @return フォロワーデータベースのデータソース
	 */
	@Bean
	@ConfigurationProperties(prefix = "follower.datasource.hikari")
	@FollowerDataSource
	public HikariDataSource followerDataSource() {
		// 以下のメソッドを参考にした。
		// org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari.dataSource(DataSourceProperties)
		DataSourceProperties properties = followerDataSourceProperties();
		HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}
}
