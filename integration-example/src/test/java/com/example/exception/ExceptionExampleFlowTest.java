package com.example.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = ExceptionExampleFlow.class)
public class ExceptionExampleFlowTest {

	@Autowired
	private MessageChannel input;
	@Autowired
	private PollableChannel output;
	@Autowired
	private PollableChannel errorChannel;

	@Test
	void test() {
		final long timeout = 1000;

		assertNull(output.receive(timeout));

		input.send(MessageBuilder.withPayload("foo").build());
		input.send(MessageBuilder.withPayload("bar").build());
		input.send(MessageBuilder.withPayload("baz").build());
		input.send(MessageBuilder.withPayload("qux").build());

		assertEquals("foo", output.receive(timeout).getPayload());
		assertEquals("qux", output.receive(timeout).getPayload());
		assertNull(output.receive(timeout));

		MyException e = (MyException) errorChannel.receive(timeout).getPayload();
		assertEquals("bar", e.getMessage());

		e = (MyException) errorChannel.receive(timeout).getPayload();
		assertEquals("baz", e.getMessage());

		assertNull(errorChannel.receive(timeout));
	}
}
