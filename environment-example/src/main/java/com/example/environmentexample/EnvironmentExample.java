package com.example.environmentexample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentExample implements EnvironmentAware, CommandLineRunner {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(environment.getProperty("example.foo"));
        System.out.println(environment.getProperty("example.bar"));
    }
}
