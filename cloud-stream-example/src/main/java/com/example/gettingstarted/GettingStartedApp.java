package com.example.gettingstarted;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GettingStartedApp {

    public static void main(String[] args) {
        SpringApplication.run(GettingStartedApp.class, args);
    }

    @Bean
    Function<String, String> uppercase() {
        return s -> s.toUpperCase();
    }
}
