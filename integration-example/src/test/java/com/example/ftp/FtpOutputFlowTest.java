package com.example.ftp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Disabled
@SpringJUnitConfig
@ContextConfiguration(classes = FtpOutputFlow.class)
class FtpOutputFlowTest {

    @Autowired
    private DirectChannel input;

    @Test
    void test() throws Exception {

        final Message<String> foo = MessageBuilder.withPayload("foo").build();
        input.send(foo);

        final Message<byte[]> bar = MessageBuilder.withPayload("bar".getBytes()).build();
        input.send(bar);

        final AtomicBoolean closed = new AtomicBoolean(false);
        final InputStream inputStream = new ByteArrayInputStream("baz".getBytes()) {
            @Override
            public void close() throws IOException {
                super.close();
                closed.set(true);
            }
        };
        final Message<InputStream> baz = MessageBuilder.withPayload(inputStream).build();
        input.send(baz);

        final Function<Message<?>, String> read = message -> {
            final String filename = message.getHeaders().getId() + ".msg";
            try {
                return new String(Files.readAllBytes(Paths.get("input", "hoge", filename)));
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        };

        assertEquals("foo", read.apply(foo));

        assertEquals("bar", read.apply(bar));

        assertEquals("baz", read.apply(baz));

        assertTrue(closed.get());
    }
}
