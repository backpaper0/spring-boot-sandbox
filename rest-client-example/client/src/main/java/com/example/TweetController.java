package com.example;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.client.Tweet;
import com.example.client.TweetClient;

@Controller
public class TweetController {

    private final TweetClient tweetClient;

    public TweetController(final TweetClient tweetClient) {
        this.tweetClient = Objects.requireNonNull(tweetClient);
    }

    @GetMapping("/")
    public ModelAndView index() {
        final List<Tweet> tweets = tweetClient.getTweets();
        return new ModelAndView("index", "tweets", tweets);
    }

    @PostMapping("/post")
    public String post(@RequestParam final String text) {
        tweetClient.postTweet(text);
        return "redirect:/";
    }
}
