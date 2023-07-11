package com.example.entity;

import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import de.huxhorn.sulky.ulid.ULID;

@Component
public class TweetCallback implements BeforeConvertCallback<Tweet> {

	private final ULID ulid;

	public TweetCallback(ULID ulid) {
		this.ulid = ulid;
	}

	@Override
	public Tweet onBeforeConvert(Tweet aggregate) {
		if (aggregate.id() != null) {
			return aggregate;
		}
		return new Tweet(ulid.nextULID(), aggregate.content());
	}
}
