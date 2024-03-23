package com.example;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

@Component
public class Dice {

    private final JdbcClient jdbc;

    public Dice(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @WithSpan
    public List<Integer> rollTheDice(@SpanAttribute int rolls) {
        return IntStream.range(0, rolls).map(a -> rollOnce()).boxed().toList();
    }

    private int rollOnce() {
        // return ThreadLocalRandom.current().nextInt(1, 7);
        return jdbc.sql("select floor(rand() * 6) + 1")
                .query(SingleColumnRowMapper.newInstance(Integer.class))
                .single();
    }
}
