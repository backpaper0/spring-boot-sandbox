package com.example.max_message_per_poll;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.endpoint.AbstractMessageSource;

@SpringBootApplication
@EnableIntegration
public class MaxMessagePerPollExample {

	@Bean
	public CounterMessageSource input() {
		return new CounterMessageSource();
	}

	@Bean
	public QueueChannel output() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlow.from(input(), this::configurePoller)
				.channel(output())
				.get();
	}

	private void configurePoller(SourcePollingChannelAdapterSpec c) {
		c.poller(Pollers.fixedRate(Duration.ofSeconds(2)).maxMessagesPerPoll(3));
	}

	static class CounterMessageSource extends AbstractMessageSource<Integer> {

		private final AtomicInteger count = new AtomicInteger();

		@Override
		public String getComponentType() {
			return "inbound-channel-adapter";
		}

		@Override
		protected Object doReceive() {
			return count.incrementAndGet();
		}
	}

}
