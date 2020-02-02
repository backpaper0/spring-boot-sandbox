package com.example.cacheexample;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RandomValue {

    @Cacheable(cacheNames = "cache1")
    public String get() {
        return UUID.randomUUID().toString();
    }
}
