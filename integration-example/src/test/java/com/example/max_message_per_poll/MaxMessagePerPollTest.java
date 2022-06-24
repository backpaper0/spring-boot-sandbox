package com.example.max_message_per_poll;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;

@SpringBootTest
public class MaxMessagePerPollTest {

	@Autowired
	private QueueChannel output;

	@Test
	void test() throws Exception {
		assertEquals(1, output.receive(100).getPayload());
		assertEquals(2, output.receive(100).getPayload());
		assertEquals(3, output.receive(100).getPayload());
		assertNull(output.receive(100));

		TimeUnit.SECONDS.sleep(2);

		assertEquals(4, output.receive(1000).getPayload());
		assertEquals(5, output.receive(1000).getPayload());
		assertEquals(6, output.receive(1000).getPayload());
		assertNull(output.receive(1000));
	}
}
