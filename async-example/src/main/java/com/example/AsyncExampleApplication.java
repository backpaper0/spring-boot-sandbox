package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncExampleApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(AsyncExampleApplication.class, args);
    }

    private final AsyncMethods methods;

    public AsyncExampleApplication(final AsyncMethods methods) {
        this.methods = methods;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println(methods.method1().get());
        System.out.println(methods.method2().get());
    }

    @Bean
    ExecutorService altExecutor() {
        return Executors.newSingleThreadExecutor(r -> new Thread(r, "alternative executor"));
    }
}
