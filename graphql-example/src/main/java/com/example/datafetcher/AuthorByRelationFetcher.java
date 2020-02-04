package com.example.datafetcher;

import java.util.concurrent.CompletionStage;
import java.util.function.ToIntFunction;

import org.dataloader.DataLoader;

import com.example.Author;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class AuthorByRelationFetcher<T> implements DataFetcher<CompletionStage<Author>> {

    private final ToIntFunction<T> idExtractor;

    public AuthorByRelationFetcher(final ToIntFunction<T> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public CompletionStage<Author> get(final DataFetchingEnvironment environment) throws Exception {
        final T source = environment.getSource();
        final int id = idExtractor.applyAsInt(source);
        final DataLoader<Integer, Author> dataLoader = environment.getDataLoader("author");
        return dataLoader.load(id);
    }
}
