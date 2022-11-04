package sample;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class Message {

	@Id
	public UUID id;
	public String text;

	public Message() {
	}

	public Message(UUID id, String text) {
		this.id = id;
		this.text = text;
	}
}
