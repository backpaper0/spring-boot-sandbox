package study.schedule;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ScheduledStudy {
    public static void main(final String[] args) {
        SpringApplication.run(ScheduledStudy.class, args);
    }

    @Scheduled(cron = "*/3 * * * * *")
    void log() {
        System.out.printf("%s %s%n", UUID.randomUUID(), LocalDateTime.now());
    }
}
