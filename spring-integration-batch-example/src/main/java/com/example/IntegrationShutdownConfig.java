package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class IntegrationShutdownConfig {

	private final ShutdownService shutdownService;
	private final JdbcOperations jdbc;

	public IntegrationShutdownConfig(ShutdownService shutdownService, JdbcOperations jdbc) {
		this.shutdownService = shutdownService;
		this.jdbc = jdbc;
	}

	@Bean
	public JdbcPollingChannelAdapter jdbcPollingChannelAdapter() {
		JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(jdbc,
				"select id from stop_signals where stopped = false");
		jdbcPollingChannelAdapter.setUpdateSql("update stop_signals set stopped = true where id = :id");
		return jdbcPollingChannelAdapter;
	}

	@Bean
	public IntegrationFlow stopIntegrationFlow() {
		return IntegrationFlows
				.from(jdbcPollingChannelAdapter(), c -> c.poller(Pollers.fixedDelay(Duration.ofSeconds(1))))
				.handle(shutdownService)
				.get();
	}
}
