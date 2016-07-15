package sample.scope;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SingletonBaz {

    private final UUID uuid = UUID.randomUUID();

    public String get() {
        return getClass().getSimpleName() + " " + uuid.toString();
    }
}
