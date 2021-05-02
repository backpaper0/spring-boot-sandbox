package com.example.transform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@EnableIntegration
public class TransformDemo {

	@Autowired
	private TransformerService service;

	@Bean
	public MessageChannel input() {
		return new DirectChannel();
	}

	@Bean
	public QueueChannel output() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlows.from(input())
				.transform(service)
				.channel(output())
				.get();
	}
}
