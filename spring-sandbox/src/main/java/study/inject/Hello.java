package study.inject;

import org.springframework.stereotype.Component;

@Component
public class Hello {

    private final World world;

    public Hello(final World world) {
        this.world = world;
    }
}
