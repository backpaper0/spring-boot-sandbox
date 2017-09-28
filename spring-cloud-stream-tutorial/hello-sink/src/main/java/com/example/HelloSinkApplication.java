package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class HelloSinkApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HelloSinkApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void print(final Tweet tweet) {
        System.out.println("Received " + tweet.text);
    }

    public static class Tweet {
        public String text;
    }
}
