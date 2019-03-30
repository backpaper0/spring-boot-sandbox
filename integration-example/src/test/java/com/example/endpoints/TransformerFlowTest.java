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
@ContextConfiguration(classes = TransformerFlow.class)
class TransformerFlowTest {

    @Autowired
    private MessageChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    void test() {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());

        assertEquals("FOO", output.receive().getPayload());
        assertEquals("BAR", output.receive().getPayload());
        assertEquals("BAZ", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
