package com.example.datafetcher;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.Book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class BooksFetcher implements DataFetcher<List<Book>> {

    private final NamedParameterJdbcTemplate jdbc;

    public BooksFetcher(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> get(final DataFetchingEnvironment environment) throws Exception {
        return jdbc.query("SELECT * FROM books", BeanPropertyRowMapper.newInstance(Book.class));
    }
}
