package com.example.core.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.core.annotation.ReadReplicaDataSource;

@Configuration(proxyBeanMethods = false)
public class ReadReplicaDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "datasource.read-replica")
	@ReadReplicaDataSource
	public DataSourceProperties readReplicaDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ReadReplicaDataSource
	public DataSource readReplicaDataSource(@ReadReplicaDataSource DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "sql.init.read-replica")
	@ReadReplicaDataSource
	public SqlInitializationProperties readReplicaSqlInitializationProperties() {
		return new SqlInitializationProperties();
	}

	@Bean
	@ReadReplicaDataSource
	public SqlDataSourceScriptDatabaseInitializer readReplicaSqlDataSourceScriptDatabaseInitializer(
			@ReadReplicaDataSource DataSource dataSource,
			@ReadReplicaDataSource SqlInitializationProperties properties) {
		return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties);
	}
}
