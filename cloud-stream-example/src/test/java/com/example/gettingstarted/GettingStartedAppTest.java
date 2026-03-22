package com.example.gettingstarted;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.support.GenericMessage;

@SpringBootTest
@EnableTestBinder
class GettingStartedAppTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @Test
    void test() {
        input.send(new GenericMessage<>("Hello World"));
        assertArrayEquals("HELLO WORLD".getBytes(), output.receive().getPayload());
    }
}
