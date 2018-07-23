package com.example.file;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FileFlow.class)
public class FileFlowTest {

    @Autowired
    private QueueChannel output;

    @Test
    public void test() {
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
