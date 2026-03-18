package com.example.api.service;

import com.example.api.configuration.EveningGreetingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.greeting.time-of-day", havingValue = "evening")
public class EveningGreetingService implements GreetingService {

    @Autowired
    private EveningGreetingProperties properties;

    @Override
    public String greet() {
        return "こんばんは" + properties.getSuffix();
    }
}
