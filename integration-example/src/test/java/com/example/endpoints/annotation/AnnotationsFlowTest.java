package com.example.endpoints.annotation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = AnnotationsFlow.class)
public class AnnotationsFlowTest {

	@Autowired
	private MessageChannel input;
	@Autowired
	private QueueChannel output;

	@Test
	void test() {
		input.send(MessageBuilder.withPayload("foo").build());
		input.send(MessageBuilder.withPayload("bar").build());
		input.send(MessageBuilder.withPayload("baz").build());
		input.send(MessageBuilder.withPayload("hoge").build());
		input.send(MessageBuilder.withPayload("fuga").build());

		assertEquals("F", output.receive().getPayload());
		assertEquals("OO", output.receive().getPayload());
		assertEquals("B", output.receive().getPayload());
		assertEquals("AR", output.receive().getPayload());
		assertEquals("B", output.receive().getPayload());
		assertEquals("AZ", output.receive().getPayload());
		assertEquals(0, output.getQueueSize());
	}
}
