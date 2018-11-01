package com.example;

import static org.assertj.core.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
public class DefaultTimeSignalTest {

    @Autowired
    private DefaultTimeSignal timeSignal;

    @Test
    public void currentDateTime() throws Exception {
        assertThat(timeSignal.currentDateTime())
                .isEqualTo("2017-11-01T09:00:00");
    }

    @TestConfiguration
    public static class TestClockProvider {
        @Bean
        public Clock clock() {
            return Clock.fixed(
                    Instant.parse("2017-11-01T00:00:00.00Z"),
                    ZoneId.of("Asia/Tokyo"));
        }
    }
}
