package com.example;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.HelloSourceApplication.Tweet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class HelloSourceApplicationTests {

    @Autowired
    private HelloSourceApplication app;
    @Autowired
    private MessageCollector collector;
    @Autowired
    private Source source;

    @Test
    @SuppressWarnings("unchecked")
    public void testTweet() {
        final Tweet tweet = new Tweet();
        tweet.text = "hello!";
        app.tweet(tweet);

        final Message<String> message = (Message<String>) collector.forChannel(source.output())
                .poll();

        assertThat(message.getPayload()).isInstanceOf(String.class);
        assertThat(message.getPayload()).isEqualTo("{\"text\":\"hello!\"}");
        assertThat(message.getHeaders().get("contentType").toString())
                .isEqualTo("application/json;charset=UTF-8");
    }
}
