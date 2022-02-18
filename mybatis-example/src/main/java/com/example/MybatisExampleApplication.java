package com.example;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MybatisExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(MybatisExampleApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "app.database-id")
	public DatabaseIdProvider databaseIdProvider() {
		return new VendorDatabaseIdProvider();
	}
}
