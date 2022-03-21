package com.example.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

@Configuration
@EnableIntegration
@ComponentScan
public class ExceptionExampleFlow {

	@Autowired
	private MessageChannel input;
	@Autowired
	private MessageChannel output;

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlows.from(input)
				.handle((String payload, MessageHeaders headers) -> payload.startsWith("b")
						? new MyException(payload)
						: payload)

				// 例外をerrorChannelへルーティングする
				.routeByException(c -> c
						.channelMapping(MyException.class, "errorChannel")
						.defaultOutputToParentFlow())

				.channel(output)
				.get();
	}
}
