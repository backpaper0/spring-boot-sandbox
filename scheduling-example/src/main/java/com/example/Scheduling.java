package com.example;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduling {

    @Scheduled(cron = "* * * * * *", zone = "Asia/Tokyo")
    public void run() {
        System.out.printf("%s - %s%n", Thread.currentThread().getName(), LocalDateTime.now());
    }
}
