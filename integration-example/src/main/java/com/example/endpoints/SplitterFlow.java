package com.example.endpoints;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;

@Configuration
@EnableIntegration
public class SplitterFlow {

	@Bean
	public DirectChannel input() {
		return new DirectChannel();
	}

	@Bean
	public QueueChannel output() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlows.from(input())
				.split(splitter())
				.channel(output())
				.get();
	}

	@Bean
	public Splitter splitter() {
		return new Splitter();
	}

	private static class Splitter extends AbstractMessageSplitter {

		@Override
		protected Object splitMessage(final Message<?> message) {
			final String value = (String) message.getPayload();
			return Stream.of(value.substring(0, 1), value.substring(1));
		}
	}
}
