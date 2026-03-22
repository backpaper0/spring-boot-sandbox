package com.example.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.function.context.FunctionProperties;
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
            assertEquals(
                    "HELLO WORLD",
                    new String(output.receive(0, "uppercase-out-0").getPayload()));
        }
    }

    @Test
    void testReverse() {
        try (var context = runApp()) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "reverse-in-0");
            assertEquals(
                    "dlroW olleH", new String(output.receive(0, "reverse-out-0").getPayload()));
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
            assertEquals("HELLO WORLD", new String(output.receive(0, "myOutput").getPayload()));
        }
    }

    @Test
    void testStreamBridge() {
        try (var context = runApp()) {
            var exampleService = context.getBean(ExampleService.class);
            exampleService.greeting();
            var output = context.getBean(OutputDestination.class);
            assertEquals("Hi!", new String(output.receive(0, "greeting").getPayload()));
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
            assertEquals("DLROW OLLEH", new String(output.receive(0, "myOutput").getPayload()));
        }
    }

    @Test
    void testComposeUppercaseReverse() {
        try (var context = runApp(false, "--spring.cloud.function.definition=uppercase|reverse")) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello World").build(), "uppercasereverse-in-0");
            assertEquals(
                    "DLROW OLLEH",
                    new String(output.receive(0, "uppercasereverse-out-0").getPayload()));
        }
    }

    @Test
    void testRouting() {
        try (var context = runApp(false, "--spring.cloud.stream.function.routing.enabled=true")) {
            var input = context.getBean(InputDestination.class);
            var output = context.getBean(OutputDestination.class);
            input.send(MessageBuilder.withPayload("Hello Uppercase")
                    .setHeader(FunctionProperties.FUNCTION_DEFINITION, "uppercase")
                    .build());
            input.send(MessageBuilder.withPayload("Hello Reverse")
                    .setHeader(FunctionProperties.FUNCTION_DEFINITION, "reverse")
                    .build());
            assertEquals("HELLO UPPERCASE", new String(output.receive().getPayload()));
            assertEquals("esreveR olleH", new String(output.receive().getPayload()));
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
