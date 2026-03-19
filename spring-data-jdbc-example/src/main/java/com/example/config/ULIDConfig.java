package com.example.config;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ULIDConfig {

    @Bean
    ULID ulid() {
        return new ULID();
    }
}
