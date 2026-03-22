package com.example.simple;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.support.MessageBuilder;

class SimpleAppTest {

    @Test
    void testUppercase() {
        try (var context = runApp()) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "uppercase-in-0");
            assertArrayEquals(
                    "HELLO WORLD".getBytes(),
                    output.receive(0, "uppercase-out-0").getPayload());
        }
    }

    @Test
    void testReverse() {
        try (var context = runApp()) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "reverse-in-0");
            assertArrayEquals(
                    "dlroW olleH".getBytes(), output.receive(0, "reverse-out-0").getPayload());
        }
    }

    @Test
    void testUppercaseWithDestinationName() {
        try (var context = runApp(
                "--spring.cloud.stream.bindings.uppercase-in-0.destination=myInput",
                "--spring.cloud.stream.bindings.uppercase-out-0.destination=myOutput")) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "myInput");
            assertArrayEquals(
                    "HELLO WORLD".getBytes(), output.receive(0, "myOutput").getPayload());
        }
    }

    @Test
    void testStreamBridge() throws Exception {
        try (var context = runApp()) {
            var exampleService = context.getBean(ExampleService.class);
            exampleService.greeting();
            var output = context.getBean(OutputDestination.class);
            assertArrayEquals("Hi!".getBytes(), output.receive(0, "greeting").getPayload());
        }
    }

    @Test
    void testUppercaseReverse() {
        try (var context = runApp(
                "--spring.cloud.stream.bindings.uppercase-in-0.destination=myInput",
                "--spring.cloud.stream.bindings.uppercase-out-0.destination=uppercase-reverse",
                "--spring.cloud.stream.bindings.reverse-in-0.destination=uppercase-reverse",
                "--spring.cloud.stream.bindings.reverse-out-0.destination=myOutput")) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "myInput");
            assertArrayEquals(
                    "DLROW OLLEH".getBytes(), output.receive(0, "myOutput").getPayload());
        }
    }

    @Test
    void testComposeUppercaseReverse() throws Exception {
        try (var context = runApp(false, "--spring.cloud.function.definition=uppercase|reverse")) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "uppercasereverse-in-0");
            assertArrayEquals(
                    "DLROW OLLEH".getBytes(),
                    output.receive(0, "uppercasereverse-out-0").getPayload());
        }
    }

    private static ConfigurableApplicationContext runApp(String... args) {
        return runApp(true, args);
    }

    private static ConfigurableApplicationContext runApp(boolean useDefaultProperties, String... args) {
        var arguments = useDefaultProperties ? Arrays.copyOf(args, args.length + 1) : args;
        if (useDefaultProperties) {
            arguments[arguments.length - 1] = "--spring.cloud.function.definition=uppercase;reverse";
        }
        return new SpringApplicationBuilder(TestChannelBinderConfiguration.getCompleteConfiguration(SimpleApp.class))
                .run(arguments);
    }
}
