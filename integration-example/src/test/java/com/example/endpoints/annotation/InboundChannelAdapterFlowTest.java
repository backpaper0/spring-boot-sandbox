package com.example.endpoints.annotation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ContextConfiguration(classes = InboundChannelAdapterFlow.class)
class InboundChannelAdapterFlowTest {

    @Autowired
    private QueueChannel output;

    @Test
    void test() {
        for (int i = 0; i < 100; i++) {
            assertEquals(i + 1, output.receive().getPayload());
        }
    }
}