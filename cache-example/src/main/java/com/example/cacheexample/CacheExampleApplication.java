package com.example.cacheexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
public class CacheExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CacheExampleApplication.class, args);
    }

    @Autowired
    private RandomValue rv;

    @GetMapping("/")
    public String get() {
        return rv.get();
    }
}
