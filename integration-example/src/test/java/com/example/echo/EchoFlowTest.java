package com.example.echo;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

public class EchoFlowTest {

    @Test
    public void test() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(EchoFlow.class);
            context.refresh();

            final MessageChannel input = context.getBean("input", MessageChannel.class);
            input.send(MessageBuilder.withPayload("foo").build());
            input.send(MessageBuilder.withPayload("bar").build());
            input.send(MessageBuilder.withPayload("baz").build());
        }
    }
}
