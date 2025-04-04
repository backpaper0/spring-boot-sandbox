package com.example;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnimalRepository {

    private final JdbcClient jdbc;

    public AnimalRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<String> findNames() {
        return jdbc.sql("select name from animals order by id asc")
                .query(SingleColumnRowMapper.newInstance(String.class))
                .list();
    }
}
