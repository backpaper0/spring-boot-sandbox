package com.example;

import java.util.Objects;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageExample implements CommandLineRunner {

    private final MessageMapper mapper;

    public MessageExample(final MessageMapper mapper) {
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    @Transactional
    public void run(final String... args) throws Exception {
        try (Cursor<Message> messages = mapper.selectAll()) {
            for (final Message message : messages) {
                System.out.println(message);
            }
        }
    }
}
