package com.example.endpoints;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = AggregatorFlow.class)
class AggregatorFlowTest {

	@Autowired
	private MessageChannel input1;
	@Autowired
	private QueueChannel output1;

	@Test
	void test1() {
		input1.send(message("foo1", "foo", 3));
		input1.send(message("bar1", "bar", 2));
		input1.send(message("foo2", "foo", 3));
		input1.send(message("bar2", "bar", 2));
		input1.send(message("foo3", "foo", 3));
		input1.send(message("baz1", "baz", 1));

		assertEquals(Arrays.asList("bar1", "bar2"), output1.receive(0).getPayload());
		assertEquals(Arrays.asList("foo1", "foo2", "foo3"), output1.receive(0).getPayload());
		assertEquals(Arrays.asList("baz1"), output1.receive(0).getPayload());
		assertEquals(0, output1.getQueueSize());
	}

	@Autowired
	private MessageChannel input2;
	@Autowired
	private QueueChannel output2;

	@Test
	void test2() {
		for (int i = 0; i < 12; i++) {
			final Message<Integer> message = MessageBuilder.withPayload(i).build();
			input2.send(message);
		}

		assertEquals(Arrays.asList(0, 4, 8), output2.receive().getPayload());
		assertEquals(Arrays.asList(1, 5, 9), output2.receive().getPayload());
		assertEquals(Arrays.asList(2, 6, 10), output2.receive().getPayload());
		assertEquals(Arrays.asList(3, 7, 11), output2.receive().getPayload());
		assertEquals(0, output2.getQueueSize());
	}

	private static Message<String> message(final String payload, final String correlationId,
			final int sequenceSize) {
		return MessageBuilder.withPayload(payload)
				.setHeader(IntegrationMessageHeaderAccessor.CORRELATION_ID, correlationId)
				.setHeader(IntegrationMessageHeaderAccessor.SEQUENCE_SIZE, sequenceSize)
				.build();
	}
}
