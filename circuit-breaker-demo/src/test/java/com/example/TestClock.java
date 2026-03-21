package com.example;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TestClock extends Clock {

    private Instant instant;
    private ZoneId zone;

    private TestClock(Instant instant, ZoneId zone) {
        this.instant = instant;
        this.zone = zone;
    }

    public void tick(long secondsToAdd) {
        this.instant = this.instant.plusSeconds(secondsToAdd);
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Instant instant() {
        return instant;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new TestClock(this.instant, zone);
    }

    public static TestClock from(Clock clock) {
        return new TestClock(clock.instant(), clock.getZone());
    }
}
