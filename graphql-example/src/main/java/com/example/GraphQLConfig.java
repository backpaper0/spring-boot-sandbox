package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

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
                        .dataFetcher("books", books())
                        .dataFetcher("book", book()))

                .type(TypeRuntimeWiring.newTypeWiring("Book")
                        .dataFetcher("author", authorByBook()))

                .build();
    }

    List<Book> books = List.of(
            new Book(1, "重力ピエロ", 3),
            new Book(2, "容疑者Xの献身", 2),
            new Book(3, "天使の耳", 2),
            new Book(4, "陽気なギャングが地球を回す", 3),
            new Book(5, "砂糖菓子の弾丸は撃ちぬけない", 1));

    List<Author> authors = List.of(
            new Author(1, "桜庭一樹"),
            new Author(2, "東野圭吾"),
            new Author(3, "伊坂幸太郎"));

    DataFetcher<List<Book>> books() {
        return env -> {
            System.out.println("books");
            return books;
        };
    }

    DataFetcher<Book> book() {
        return env -> {
            System.out.println("book");
            final int id = Integer.parseInt(env.<String> getArgument("id"));
            return books.stream().filter(a -> a.getId() == id).findAny().orElse(null);
        };
    }

    DataFetcher<Author> authorByBook() {
        return env -> {
            System.out.println("authorByBook");
            final Book book = env.getSource();
            final int id = book.getAuthorId();
            return authors.stream().filter(a -> a.getId() == id).findAny().orElse(null);
        };
    }
}