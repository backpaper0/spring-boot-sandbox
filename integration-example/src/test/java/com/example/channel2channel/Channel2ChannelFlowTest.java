package com.example.channel2channel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.channel2channel.Channel2ChannelFlow;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Channel2ChannelFlow.class)
public class Channel2ChannelFlowTest {

    @Autowired
    private MessageChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    public void test() {
        input.send(MessageBuilder.withPayload("foobar").build());

        assertEquals("foobar", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
