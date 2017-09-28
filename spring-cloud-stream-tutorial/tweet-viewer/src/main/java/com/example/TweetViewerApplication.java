package com.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class TweetViewerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TweetViewerApplication.class, args);
    }

    private final List<Tweet> tweets = new CopyOnWriteArrayList<>();

    @GetMapping
    public List<Tweet> viewTweets() {
        return tweets;
    }

    @StreamListener(Sink.INPUT)
    public void collect(final Tweet tweet) {
        tweets.add(tweet);
    }

    public static class Tweet {
        public String text;
    }
}