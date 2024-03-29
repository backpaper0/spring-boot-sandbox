package com.example.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = RouterFlow.class)
class RouterFlowTest {

	@Autowired
	private MessageChannel input;
	@Autowired
	private QueueChannel output0;
	@Autowired
	private QueueChannel output1;
	@Autowired
	private QueueChannel output2;

	@Test
	void test() {
		for (int i = 0; i < 10; i++) {
			input.send(MessageBuilder.withPayload(i).build());
		}

		assertEquals(1, output0.receive().getPayload());
		assertEquals(5, output0.receive().getPayload());
		assertEquals(7, output0.receive().getPayload());
		assertEquals(0, output0.getQueueSize());

		assertEquals(0, output1.receive().getPayload());
		assertEquals(2, output1.receive().getPayload());
		assertEquals(4, output1.receive().getPayload());
		assertEquals(6, output1.receive().getPayload());
		assertEquals(8, output1.receive().getPayload());
		assertEquals(0, output1.getQueueSize());

		assertEquals(0, output2.receive().getPayload());
		assertEquals(3, output2.receive().getPayload());
		assertEquals(6, output2.receive().getPayload());
		assertEquals(9, output2.receive().getPayload());
		assertEquals(0, output2.getQueueSize());
	}
}
