package com.example;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class TwoDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@Primary
	public HikariDataSource dataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.sql.init")
	@Primary
	public DatabaseInitializationSettings databaseInitializationSettings() {
		return new DatabaseInitializationSettings();
	}

	@Bean
	@Primary
	public DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(
			DataSource dataSource,
			DatabaseInitializationSettings settings) {
		return new DataSourceScriptDatabaseInitializer(dataSource, settings);
	}

	@Bean
	@ConfigurationProperties(prefix = "secondary.datasource")
	@Secondary
	public DataSourceProperties secondaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "secondary.datasource.hikari")
	@Secondary
	public HikariDataSource secondaryDataSource(@Secondary DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "secondary.sql.init")
	@Secondary
	public DatabaseInitializationSettings secondaryDatabaseInitializationSettings() {
		return new DatabaseInitializationSettings();
	}

	@Bean
	@Secondary
	public DataSourceScriptDatabaseInitializer secondaryDataSourceInitializer(
			@Secondary DataSource dataSource,
			@Secondary DatabaseInitializationSettings settings) {
		return new DataSourceScriptDatabaseInitializer(dataSource, settings);
	}
}
