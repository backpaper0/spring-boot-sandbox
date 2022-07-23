package com.example.core.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.example.core.annotation.LeaderDataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class LeaderConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@LeaderDataSource
	public DataSourceProperties leaderDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@LeaderDataSource
	public HikariDataSource leaderDataSource() {
		DataSourceProperties properties = leaderDataSourceProperties();
		HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		if (StringUtils.hasText(properties.getName())) {
			dataSource.setPoolName(properties.getName());
		}
		return dataSource;
	}

	@Bean
	@LeaderDataSource
	@ConfigurationProperties(prefix = "spring.sql.init")
	public SqlInitializationProperties leaderSqlInitializationProperties() {
		return new SqlInitializationProperties();
	}

	@SuppressWarnings("resource")
	@Bean
	@LeaderDataSource
	public SqlDataSourceScriptDatabaseInitializer leaderSqlDataSourceScriptDatabaseInitializer() {
		return new SqlDataSourceScriptDatabaseInitializer(leaderDataSource(), leaderSqlInitializationProperties());
	}
}
