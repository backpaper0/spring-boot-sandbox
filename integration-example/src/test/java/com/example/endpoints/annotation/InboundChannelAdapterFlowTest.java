package com.example.endpoints.annotation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InboundChannelAdapterFlow.class)
public class InboundChannelAdapterFlowTest {

    @Autowired
    private QueueChannel output;

    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            assertEquals(i + 1, output.receive().getPayload());
        }
    }
}