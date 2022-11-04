package com.example.endpoints;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class RouterFlow {

	@Bean
	public DirectChannel input() {
		return new DirectChannel();
	}

	@Bean
	public QueueChannel output0() {
		return new QueueChannel();
	}

	@Bean
	public QueueChannel output1() {
		return new QueueChannel();
	}

	@Bean
	public QueueChannel output2() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlows.from(input())
				.route(router())
				.get();
	}

	@Bean
	public Router router() {
		final Router router = new Router(output1(), output2());
		router.setDefaultOutputChannel(output0());
		return router;
	}

	private static class Router extends AbstractMessageRouter {

		private final MessageChannel output1;
		private final MessageChannel output2;

		public Router(final MessageChannel output1, final MessageChannel output2) {
			this.output1 = output1;
			this.output2 = output2;
		}

		@Override
		protected Collection<MessageChannel> determineTargetChannels(final Message<?> message) {
			final Collection<MessageChannel> channels = new ArrayList<>();
			final int value = (Integer) message.getPayload();
			if (value % 2 == 0) {
				channels.add(output1);
			}
			if (value % 3 == 0) {
				channels.add(output2);
			}
			return channels;
		}
	}
}
