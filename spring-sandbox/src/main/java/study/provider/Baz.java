package study.provider;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Baz {

    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}