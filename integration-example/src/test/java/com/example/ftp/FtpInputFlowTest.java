package com.example.ftp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Disabled
@SpringJUnitConfig
@ContextConfiguration(classes = FtpInputFlow.class)
class FtpInputFlowTest {

    @Autowired
    private QueueChannel output;

    @Test
    void test() {
        assertEquals("foo", output.receive().getPayload());
        assertEquals("bar", output.receive().getPayload());
        assertEquals("baz", output.receive().getPayload());
        assertEquals("qux", output.receive().getPayload());
        assertEquals("hoge", output.receive().getPayload());
        assertEquals("fuga", output.receive().getPayload());
        assertEquals("piyo", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
