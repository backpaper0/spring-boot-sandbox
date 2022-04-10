package com.example.core.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.core.annotation.PrimaryDataSource;

@Configuration(proxyBeanMethods = false)
public class PrimaryDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "datasource.primary")
	@PrimaryDataSource
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@PrimaryDataSource
	public DataSource primaryDataSource(@PrimaryDataSource DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@PrimaryDataSource
	@ConfigurationProperties(prefix = "sql.init.primary")
	public SqlInitializationProperties primarySqlInitializationProperties() {
		return new SqlInitializationProperties();
	}

	@Bean
	@PrimaryDataSource
	public SqlDataSourceScriptDatabaseInitializer primarySqlDataSourceScriptDatabaseInitializer(
			@PrimaryDataSource DataSource dataSource,
			@PrimaryDataSource SqlInitializationProperties properties) {
		return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties);
	}
}
