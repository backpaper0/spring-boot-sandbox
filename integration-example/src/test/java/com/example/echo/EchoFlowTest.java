package com.example.echo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

class EchoFlowTest {

	@Test
	void test() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(EchoFlow.class);
			context.refresh();

			final MessageChannel input = context.getBean("input", MessageChannel.class);
			input.send(MessageBuilder.withPayload("foo").build());
			input.send(MessageBuilder.withPayload("bar").build());
			input.send(MessageBuilder.withPayload("baz").build());

			final PollableChannel output = context.getBean("output", PollableChannel.class);
			assertEquals("foo", output.receive().getPayload());
			assertEquals("bar", output.receive().getPayload());
			assertEquals("baz", output.receive().getPayload());
			assertNull(output.receive(0));
		}
	}
}
