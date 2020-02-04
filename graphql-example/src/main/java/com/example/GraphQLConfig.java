package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.context.annotation.RequestScope;

import com.example.datafetcher.AuthorByRelationFetcher;
import com.example.datafetcher.BookFetcher;
import com.example.datafetcher.BooksFetcher;
import com.example.dataloader.AuthorLoader;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Configuration
public class GraphQLConfig {

    @Value("classpath:/schema.graphqls")
    private Resource resource;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

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

    @Bean
    @RequestScope //HashMapでキャッシュされちゃうのでRequestScopeにした方が良い気がする
    public DataLoaderRegistry dataLoaderRegistry() {
        return new DataLoaderRegistry()
                .register("author", authorDataLoader());
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()

                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("books", books())
                        .dataFetcher("book", book()))

                .type(TypeRuntimeWiring.newTypeWiring("Book")
                        .dataFetcher("author", authorByBook()))

                .build();
    }

    DataFetcher<List<Book>> books() {
        return new BooksFetcher(jdbc);
    }

    DataFetcher<Book> book() {
        return new BookFetcher(jdbc);
    }

    DataFetcher<CompletionStage<Author>> authorByBook() {
        return new AuthorByRelationFetcher<>(Book::getAuthorId);
    }

    DataLoader<Integer, Author> authorDataLoader() {
        return DataLoader.newDataLoader(new AuthorLoader(jdbc));
    }
}