package com.example.echo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
@EnableIntegration
public class EchoFlow {

	@Bean
	public MessageChannel input() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlow.from(input())
				.channel(output())
				.get();
	}

	@Bean
	public PollableChannel output() {
		return new QueueChannel();
	}
}
