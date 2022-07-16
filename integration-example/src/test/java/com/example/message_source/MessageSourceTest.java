package com.example.message_source;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;

@SpringBootTest
@Tag("FailOnGitHub")
public class MessageSourceTest {

	@Autowired
	private QueueChannel output;

	@Test
	void test() throws Exception {
		long timeout = 200L;
		assertEquals(1, output.receive(timeout).getPayload());
		assertEquals(2, output.receive(timeout).getPayload());
		assertEquals(3, output.receive(timeout).getPayload());
		assertNull(output.receive(0));
	}
}
