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
@ContextConfiguration(classes = SplitterFlow.class)
class SplitterFlowTest {

    @Autowired
    private MessageChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    void test() {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());

        assertEquals("f", output.receive().getPayload());
        assertEquals("oo", output.receive().getPayload());
        assertEquals("b", output.receive().getPayload());
        assertEquals("ar", output.receive().getPayload());
        assertEquals("b", output.receive().getPayload());
        assertEquals("az", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
