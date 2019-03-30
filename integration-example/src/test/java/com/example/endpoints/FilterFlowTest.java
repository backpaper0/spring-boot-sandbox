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
@ContextConfiguration(classes = FilterFlow.class)
class FilterFlowTest {

    @Autowired
    private MessageChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    void test() {
        for (int i = 0; i < 10; i++) {
            input.send(MessageBuilder.withPayload(i).build());
        }

        assertEquals(1, output.receive().getPayload());
        assertEquals(3, output.receive().getPayload());
        assertEquals(5, output.receive().getPayload());
        assertEquals(7, output.receive().getPayload());
        assertEquals(9, output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
