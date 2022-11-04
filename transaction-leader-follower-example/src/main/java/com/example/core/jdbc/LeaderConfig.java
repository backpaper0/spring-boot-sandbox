package com.example.core.jdbc;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.example.core.annotation.LeaderDataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * リーダーデータベースの設定。
 *
 */
@Configuration
public class LeaderConfig {

	/**
	 * リーダーデータベースのプロパティを構築する。
	 *
	 * @return リーダーデータベースのプロパティ
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@LeaderDataSource
	public DataSourceProperties leaderDataSourceProperties() {
		return new DataSourceProperties();
	}

	/**
	 * リーダーデータベースのデータソースを構築する。
	 *
	 * @return リーダーデータベースのデータソース
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@LeaderDataSource
	public HikariDataSource leaderDataSource() {
		// 以下のメソッドを参考にした。
		// org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari.dataSource(DataSourceProperties)
		DataSourceProperties properties = leaderDataSourceProperties();
		HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}
}
