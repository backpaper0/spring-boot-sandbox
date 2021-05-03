package com.example.transform;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootTest
public class TransformTest {

	@Autowired
	private MessageChannel input;
	@Autowired
	private QueueChannel output;

	@Test
	void test() throws Exception {
		input.send(new GenericMessage<>("foo"));
		input.send(new GenericMessage<>("bar"));

		long timeout = 100L;
		assertEquals("*foo*", output.receive(timeout).getPayload());
		assertEquals("*bar*", output.receive(timeout).getPayload());
		assertNull(output.receive(timeout));
	}
}
