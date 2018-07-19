package com.example.endpoints;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TransformerFlow.class)
public class TransformerFlowTest {

    @Autowired
    private MessageChannel input;
    @Autowired
    private PollableChannel output;

    @Test
    public void test() {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());

        assertEquals("FOO", output.receive().getPayload());
        assertEquals("BAR", output.receive().getPayload());
        assertEquals("BAZ", output.receive().getPayload());
        assertNull(output.receive(0));
    }
}
