package com.example.endpoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
@EnableIntegration
public class AggregatorFlow {

	@Bean
	public DirectChannel input1() {
		return new DirectChannel();
	}

	@Bean
	public QueueChannel output1() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow1() {
		return IntegrationFlow.from(input1())
				.aggregate()
				.channel(output1())
				.get();
	}

	@Bean
	public DirectChannel input2() {
		return new DirectChannel();
	}

	@Bean
	public QueueChannel output2() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow2() {
		return IntegrationFlow.from(input2())
				.aggregate(a -> a.correlationStrategy(correlationStrategy())
						.releaseStrategy(releaseStrategy()))
				.channel(output2())
				.get();
	}

	@Bean
	public CorrelationStrategy correlationStrategy() {
		return message -> {
			final int payload = (Integer) message.getPayload();
			return payload % 4;
		};
	}

	@Bean
	public ReleaseStrategy releaseStrategy() {
		return group -> group.size() == 3;
	}
}
