package com.example.endpoints;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceActivatorFlow.class)
public class ServiceActivatorFlowTest {

    @Autowired
    private DirectChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    public void test() {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());

        assertEquals("FOO", output.receive().getPayload());
        assertEquals("BAR", output.receive().getPayload());
        assertEquals("BAZ", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
