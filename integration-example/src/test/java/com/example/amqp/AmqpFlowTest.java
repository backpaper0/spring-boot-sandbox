package com.example.amqp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = { AmqpFlow.class, RabbitAutoConfiguration.class })
class AmqpFlowTest {

    @Autowired
    private DirectChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    void test() throws Exception {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());

        assertEquals("foo", output.receive().getPayload());
        assertEquals("bar", output.receive().getPayload());
        assertEquals("baz", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
