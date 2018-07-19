package com.example.endpoints;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

public class TransformerFlowTest {

    @Test
    public void test() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(TransformerFlow.class);
            context.refresh();

            final MessageChannel input = context.getBean("input", MessageChannel.class);
            input.send(MessageBuilder.withPayload("foo").build());
            input.send(MessageBuilder.withPayload("bar").build());
            input.send(MessageBuilder.withPayload("baz").build());

            final PollableChannel output = context.getBean("output", PollableChannel.class);
            assertEquals("FOO", output.receive().getPayload());
            assertEquals("BAR", output.receive().getPayload());
            assertEquals("BAZ", output.receive().getPayload());
            assertNull(output.receive(0));
        }
    }
}
