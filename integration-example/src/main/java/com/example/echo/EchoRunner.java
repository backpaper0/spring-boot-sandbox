package com.example.echo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class EchoRunner implements CommandLineRunner {

    private final MessageChannel input;

    public EchoRunner(final MessageChannel input) {
        this.input = input;
    }

    @Override
    public void run(final String... args) throws Exception {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());
    }
}
