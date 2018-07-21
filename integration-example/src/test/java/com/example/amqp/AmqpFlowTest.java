package com.example.amqp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre>
 * docker run -d --name mq -h usaq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
 * </pre>
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AmqpFlow.class, RabbitAutoConfiguration.class })
public class AmqpFlowTest {

    @Autowired
    private DirectChannel input;
    @Autowired
    private QueueChannel output;

    @Test
    public void test() throws Exception {
        input.send(MessageBuilder.withPayload("foo").build());
        input.send(MessageBuilder.withPayload("bar").build());
        input.send(MessageBuilder.withPayload("baz").build());

        assertEquals("foo", output.receive().getPayload());
        assertEquals("bar", output.receive().getPayload());
        assertEquals("baz", output.receive().getPayload());
        assertEquals(0, output.getQueueSize());
    }
}
