package com.example;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.client.R2dbc;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DatabaseConfig {

	private final String url;
	private final String username;
	private final String password;

	public DatabaseConfig(
			@Value("${spring.datasource.url}") final String url,
			@Value("${spring.datasource.username}") final String username,
			@Value("${spring.datasource.password}") final String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Bean
	public ConnectionFactory connectionFactory() {

		// URI.create("postgresql://localhost:5432/example");
		final URI uri = URI.create(url.substring("jdbc:".length()));

		final PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration
				.builder()
				.host(uri.getHost())
				.port(uri.getPort())
				.database(uri.getPath().substring("/".length()))
				.username(username)
				.password(password)
				.build();

		return new PostgresqlConnectionFactory(configuration);
	}

	@Bean
	public R2dbc r2dbc() {
		return new R2dbc(connectionFactory());
	}
}
