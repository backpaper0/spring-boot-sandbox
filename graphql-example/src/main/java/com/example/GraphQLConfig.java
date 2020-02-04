package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.context.annotation.RequestScope;

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
        return env -> {
            System.out.println("books");
            return jdbc.query("SELECT * FROM books", BeanPropertyRowMapper.newInstance(Book.class));
        };
    }

    DataFetcher<Book> book() {
        return env -> {
            System.out.println("book");
            final int id = Integer.parseInt(env.<String> getArgument("id"));
            return jdbc.queryForObject("SELECT * FROM books WHERE id = :id",
                    new MapSqlParameterSource().addValue("id", id),
                    BeanPropertyRowMapper.newInstance(Book.class));
        };
    }

    DataFetcher<CompletionStage<Author>> authorByBook() {
        return env -> {
            System.out.println("authorByBook");
            final Book book = env.getSource();
            final int id = book.getAuthorId();
            final DataLoader<Integer, Author> dataLoader = env.getDataLoader("author");
            return dataLoader.load(id);
        };
    }

    DataLoader<Integer, Author> authorDataLoader() {
        final BatchLoader<Integer, Author> batchLoadFunction = keys -> {
            System.out.println("authorDataLoader");
            final Map<Integer, Author> authors = jdbc
                    .query("SELECT * FROM authors WHERE id IN (:ids)",
                            new MapSqlParameterSource().addValue("ids", keys),
                            BeanPropertyRowMapper.newInstance(Author.class))
                    .stream().collect(Collectors.toMap(Author::getId, Function.identity()));
            return CompletableFuture
                    .completedStage(keys.stream().map(authors::get).collect(Collectors.toList()));
        };
        return DataLoader.newDataLoader(batchLoadFunction);
    }
}