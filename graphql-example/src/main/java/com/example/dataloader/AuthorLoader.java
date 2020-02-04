package com.example.dataloader;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.dataloader.BatchLoader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.Author;

public class AuthorLoader implements BatchLoader<Integer, Author> {

    private final NamedParameterJdbcTemplate jdbc;

    public AuthorLoader(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public CompletionStage<List<Author>> load(final List<Integer> keys) {

        final Map<Integer, Author> authors = jdbc
                .query("SELECT * FROM authors WHERE id IN (:ids)",
                        new MapSqlParameterSource().addValue("ids", keys),
                        BeanPropertyRowMapper.newInstance(Author.class))
                .stream().collect(Collectors.toMap(Author::getId, Function.identity()));

        return CompletableFuture
                .completedStage(keys.stream().map(authors::get).collect(Collectors.toList()));
    }
}
