package com.example.profile.configuration;

import com.example.profile.component.FoobarFactory;
import com.example.profile.component.FoobarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoobarConfig {

    @Autowired
    private FoobarFactory foobarFactory;

    @Bean
    public FoobarService foobarService() {
        return new FoobarService(foobarFactory.create("example"));
    }
}
