package com.example.testing;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class DefaultTimeSignal implements TimeSignal {

    private final Clock clock;

    public DefaultTimeSignal(final Clock clock) {
        this.clock = Objects.requireNonNull(clock);
    }

    @Override
    public LocalDateTime currentDateTime() {
        return LocalDateTime.now(clock);
    }
}
