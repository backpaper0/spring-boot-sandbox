package com.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final List<Tweet> tweets = new CopyOnWriteArrayList<>();

    @GetMapping
    public List<Tweet> getTweets() {
        return tweets;
    }

    @PostMapping
    public void tweet(@RequestParam final String text, final Authentication authentication) {
        tweets.add(new Tweet(authentication.getName(), text));
    }

    public static class Tweet {

        private final String username;
        private final String text;

        public Tweet(final String username, final String text) {
            this.username = username;
            this.text = text;
        }

        public String getUsername() {
            return username;
        }

        public String getText() {
            return text;
        }
    }
}
