package com.example.datafetcher;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.Book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class BookFetcher implements DataFetcher<Book> {

    private final NamedParameterJdbcTemplate jdbc;

    public BookFetcher(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book get(final DataFetchingEnvironment environment) throws Exception {
        final int id = Integer.parseInt(environment.<String> getArgument("id"));
        return jdbc.queryForObject("SELECT * FROM books WHERE id = :id",
                new MapSqlParameterSource().addValue("id", id),
                BeanPropertyRowMapper.newInstance(Book.class));
    }
}
