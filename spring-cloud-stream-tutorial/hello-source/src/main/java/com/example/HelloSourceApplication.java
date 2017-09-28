package com.example;

import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Source.class)
public class HelloSourceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HelloSourceApplication.class, args);
    }

    private final Source source;

    public HelloSourceApplication(final Source source) {
        this.source = Objects.requireNonNull(source);
    }

    @PostMapping
    public void tweet(@RequestBody final Tweet tweet) {
        source.output().send(MessageBuilder.withPayload(tweet).build());
    }

    public static class Tweet {
        public String text;
    }
}
