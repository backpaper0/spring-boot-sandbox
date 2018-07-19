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
@ContextConfiguration(classes = RouterFlow.class)
public class RouterFlowTest {

    @Autowired
    private MessageChannel input;
    @Autowired
    private PollableChannel output0;
    @Autowired
    private PollableChannel output1;
    @Autowired
    private PollableChannel output2;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            input.send(MessageBuilder.withPayload(i).build());
        }

        assertEquals(1, output0.receive().getPayload());
        assertEquals(5, output0.receive().getPayload());
        assertEquals(7, output0.receive().getPayload());
        assertNull(output0.receive(0));

        assertEquals(0, output1.receive().getPayload());
        assertEquals(2, output1.receive().getPayload());
        assertEquals(4, output1.receive().getPayload());
        assertEquals(6, output1.receive().getPayload());
        assertEquals(8, output1.receive().getPayload());
        assertNull(output1.receive(0));

        assertEquals(0, output2.receive().getPayload());
        assertEquals(3, output2.receive().getPayload());
        assertEquals(6, output2.receive().getPayload());
        assertEquals(9, output2.receive().getPayload());
        assertNull(output2.receive(0));
    }
}
