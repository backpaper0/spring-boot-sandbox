package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import graphql.spring.web.servlet.GraphQLEndpointConfiguration;

@SpringBootApplication(exclude = { GraphQLEndpointConfiguration.class })
public class SpringBootGraphqlDemoApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootGraphqlDemoApplication.class, args);
    }
}
