package com.example;

import static org.assertj.core.api.Assertions.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.HelloSinkApplication.Tweet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class HelloSinkApplicationTests {

    @Autowired
    private Sink sink;
    @Rule
    public OutputCapture capture = new OutputCapture();

    @Test
    public void testPrint() {
        final Tweet tweet = new Tweet();
        tweet.text = "Hello";
        sink.input().send(MessageBuilder.withPayload(tweet).build());

        assertThat(capture.toString())
                .isEqualTo("Received Hello" + System.lineSeparator());
    }
}
