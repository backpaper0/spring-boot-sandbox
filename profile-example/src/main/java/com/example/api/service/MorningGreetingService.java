package com.example.api.service;

import com.example.api.configuration.MorningGreetingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.greeting.time-of-day", havingValue = "morning")
public class MorningGreetingService implements GreetingService {

    @Autowired
    private MorningGreetingProperties properties;

    @Override
    public String greet() {
        return properties.getPrefix() + "おはようございます";
    }
}
