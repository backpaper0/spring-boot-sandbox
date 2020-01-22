package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Configuration
public class GraphQLProvider {

    @Value("classpath:/schema.graphqls")
    private Resource resource;

    @Autowired
    private GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() throws IOException {
        try (Reader in = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            final TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(in);
            final RuntimeWiring runtimeWiring = buildWiring();
            final SchemaGenerator schemaGenerator = new SchemaGenerator();
            final GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry,
                    runtimeWiring);
            return GraphQL.newGraphQL(graphQLSchema).build();
        }
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("category", graphQLDataFetchers.queryCategory())
                        .dataFetcher("categories", graphQLDataFetchers.queryCategories()))
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                        .dataFetcher("category", graphQLDataFetchers.mutateCategory()))
                .build();
    }
}