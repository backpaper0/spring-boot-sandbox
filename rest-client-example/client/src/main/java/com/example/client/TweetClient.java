package com.example.client;

import java.util.List;

public interface TweetClient {

    List<Tweet> getTweets();

    void postTweet(String text);
}
