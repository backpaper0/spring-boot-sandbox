package com.example.core.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.example.core.annotation.FollowerDataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class FollowerConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "follower.datasource")
	@FollowerDataSource
	public DataSourceProperties followerDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "follower.datasource.hikari")
	@FollowerDataSource
	public HikariDataSource followerDataSource() {
		DataSourceProperties properties = followerDataSourceProperties();
		HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}

	@Bean
	@ConfigurationProperties(prefix = "follower.sql.init")
	@FollowerDataSource
	public SqlInitializationProperties followerSqlInitializationProperties() {
		return new SqlInitializationProperties();
	}

	@SuppressWarnings("resource")
	@Bean
	@FollowerDataSource
	public SqlDataSourceScriptDatabaseInitializer followerSqlDataSourceScriptDatabaseInitializer() {
		return new SqlDataSourceScriptDatabaseInitializer(followerDataSource(), followerSqlInitializationProperties());
	}
}
