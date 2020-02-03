package com.example;

import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.spring.web.servlet.ExecutionResultHandler;
import graphql.spring.web.servlet.GraphQLInvocation;
import graphql.spring.web.servlet.GraphQLInvocationData;
import graphql.spring.web.servlet.components.DefaultExecutionResultHandler;
import graphql.spring.web.servlet.components.GraphQLController;

@Configuration
public class GraphQLWebMvcConfig {

    @Bean
    public ExecutionResultHandler executionResultHandler() {
        return new DefaultExecutionResultHandler();
    }

    @Bean
    public GraphQLInvocation graphQLInvocation() {
        return new GraphQLInvocationImpl();
    }

    @Bean
    public GraphQLController graphQLController() {
        return new GraphQLController();
    }

    public static class GraphQLInvocationImpl implements GraphQLInvocation {

        @Autowired
        private GraphQL graphQL;
        @Autowired
        private DataLoaderRegistry dataLoaderRegistry;

        @Override
        public CompletableFuture<ExecutionResult> invoke(final GraphQLInvocationData invocationData,
                final WebRequest webRequest) {
            final ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                    .query(invocationData.getQuery())
                    .operationName(invocationData.getOperationName())
                    .variables(invocationData.getVariables())
                    .dataLoaderRegistry(dataLoaderRegistry)
                    .build();
            return graphQL.executeAsync(executionInput);
        }

    }
}
