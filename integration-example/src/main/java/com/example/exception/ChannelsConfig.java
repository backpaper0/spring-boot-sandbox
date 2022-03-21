package com.example.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
public class ChannelsConfig {

	@Bean
	public MessageChannel input() {
		return new DirectChannel();
	}

	@Bean
	public PollableChannel output() {
		return new QueueChannel();
	}

	@Bean
	public PollableChannel errorChannel() {
		// errorChannelという名前のbean
		// デフォルトはSpringによってPublishSubscribeChannelが用意される
		// receiveメソッドでメッセージを取り出したいため自分で定義している
		return new QueueChannel();
	}
}
