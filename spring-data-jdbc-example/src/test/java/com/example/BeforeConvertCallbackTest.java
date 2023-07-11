package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.entity.Tweet;
import com.example.repository.TweetRepository;

@SpringBootTest
public class BeforeConvertCallbackTest {

	@Autowired
	private TweetRepository tweets;

	@Test
	void insert() {
		Tweet tweet = new Tweet(null, "Hello World");
		Tweet actual = tweets.save(tweet);

		assertAll(
				() -> assertNotNull(actual.id()),
				() -> assertEquals(tweet.content(), actual.content()));
	}

	@Test
	void update() {
		Tweet tweet = tweets.findById("01H53KCNBZ9MMFBN7TDDPTRS6B").get();
		Tweet actual = tweets.save(tweet);

		assertEquals(tweet, actual);
	}
}
