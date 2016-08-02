package sample;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class Message {

    @Id
    public String id;
    public String text;

    public Message() {
    }

    public Message(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
