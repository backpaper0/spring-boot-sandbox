package com.example.channel2channel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = Channel2ChannelFlow.class)
class Channel2ChannelFlowTest {

	@Autowired
	private MessageChannel input;
	@Autowired
	private QueueChannel output;

	@Test
	void test() {
		input.send(MessageBuilder.withPayload("foobar").build());

		assertEquals("foobar", output.receive().getPayload());
		assertEquals(0, output.getQueueSize());
	}
}
