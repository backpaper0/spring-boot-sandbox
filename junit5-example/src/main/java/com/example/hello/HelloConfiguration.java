package com.example.hello;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.hello.impl.DefaultHello;
import com.example.hello.impl.MessageFormatter;

@Configuration
@ComponentScan
public class HelloConfiguration {

    private final MessageFormatter messageFormatter;

    public HelloConfiguration(final MessageFormatter messageFormatter) {
        this.messageFormatter = Objects.requireNonNull(messageFormatter);
    }

    @Bean
    public Hello hello() {
        return new DefaultHello(messageFormatter, "world");
    }
}
