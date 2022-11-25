package com.example.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
@EnableIntegration
@Import({ DataSourceAutoConfiguration.class, SqlInitializationAutoConfiguration.class })
public class JdbcInputFlow {

	private final DataSource dataSource;

	public JdbcInputFlow(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public QueueChannel output() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlow
				.from(jdbcPollingChannelAdapter(), c -> c.poller(Pollers.fixedRate(100)))
				.channel(output())
				.get();
	}

	@Bean
	public JdbcPollingChannelAdapter jdbcPollingChannelAdapter() {
		final JdbcPollingChannelAdapter adapter = new JdbcPollingChannelAdapter(dataSource,
				"SELECT * FROM messages WHERE sent = 0 ORDER BY id ASC");
		adapter.setUpdatePerRow(true);
		adapter.setUpdateSql("UPDATE messages SET sent = 1 WHERE id = :id");
		adapter.setRowMapper(new BeanPropertyRowMapper<>(MyMessage.class));
		return adapter;
	}

}
