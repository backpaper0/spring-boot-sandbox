package com.example.config;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourcesConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
		return dataSourceProperties
				.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}

	@SuppressWarnings("resource")
	@Bean
	@AppDataSource
	public DataSourceTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(dataSource(null));
	}

	@Bean
	@BatchDataSource
	public EmbeddedDatabase batchDataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName("batch")
				.build();
	}
}
