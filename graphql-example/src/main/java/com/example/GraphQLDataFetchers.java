package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {

    private final List<Category> categories = new ArrayList<>(List.of(
            new Category(1, "foo"),
            new Category(2, "bar"),
            new Category(3, "baz")));

    public DataFetcher<Category> queryCategory() {
        return env -> {
            final int id = Integer.parseInt(env.getArgument("id"));
            return categories
                    .stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<List<Category>> queryCategories() {
        return env -> categories;
    }

    public DataFetcher<Category> mutateCategory() {
        return env -> {
            final String name = env.getArgument("name");
            final int id = categories.size() + 1;
            final Category c = new Category(id, name);
            categories.add(c);
            return c;
        };
    }
}