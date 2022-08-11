package com.example.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Spring Batchの設定。
 * EnableBatchProcessingで適用されるSimpleBatchConfigurationの代わりとなる。
 *
 */
@Configuration(proxyBeanMethods = false)
public class SpringBatchConfig extends AbstractBatchConfiguration {

	@Autowired
	private BatchConfigurer configurer;
	@Autowired
	private DataSource dataSource;

	@Bean
	@Override
	public JobRepository jobRepository() throws Exception {
		return configurer.getJobRepository();
	}

	@Bean
	@Override
	public JobLauncher jobLauncher() throws Exception {
		return configurer.getJobLauncher();
	}

	@Bean
	@Override
	public JobExplorer jobExplorer() throws Exception {
		return configurer.getJobExplorer();
	}

	@Bean
	@Override
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
