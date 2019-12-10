package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Types;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;

@SpringBootTest
class EnumParameterTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void test() throws Exception {

        final Object txt = new SqlParameterValue(Types.VARCHAR, Txt.FOO) {
            @Override
            public Object getValue() {
                return ((Txt) super.getValue()).name().toLowerCase();
            }
        };

        final Map<String, Object> result = jdbcTemplate
                .queryForMap("SELECT id, txt, dt FROM example WHERE txt = ?", txt);

        assertEquals("foo", result.get("txt"));
    }

    enum Txt {
        FOO, BAR, BAZ
    }
}
