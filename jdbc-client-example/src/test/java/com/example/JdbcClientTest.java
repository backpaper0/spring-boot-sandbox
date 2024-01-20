package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.entity.CudExample;
import com.example.entity.RExample;

@SpringBootTest
public class JdbcClientTest {

    @Autowired
    JdbcClient jdbc;

    @Test
    void findOne() {
        RExample actual = jdbc.sql("select * from r_example where id = ?")
                .param(2)
                .query(RExample.class)
                .optional()
                .get();
        assertEquals(new RExample(2, "bar"), actual);
    }

    @Test
    void findList() {
        List<RExample> actual = jdbc.sql("select * from r_example where id in (:id1, :id2) order by id asc")
                .param("id1", 2)
                .param("id2", 3)
                .query(RExample.class)
                .list();
        List<RExample> expected = List.of(new RExample(2, "bar"), new RExample(3, "baz"));
        assertEquals(expected, actual);
    }

    @Test
    void insert() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int actual1 = jdbc.sql("insert into cud_example (text_content) values (:textContent)")
                .param("textContent", "baz")
                .update(keyHolder);
        assertEquals(1, actual1);
        Number key = keyHolder.getKey();
        assertNotNull(key);

        CudExample actual2 = jdbc.sql("select * from cud_example where id = ?")
                .param(key)
                .query(CudExample.class)
                .single();
        assertEquals(new CudExample(key.intValue(), "baz"), actual2);
    }

    @Test
    void update() {
        int actual1 = jdbc.sql("update cud_example set text_content = :textContent where id = :id")
                .param("textContent", "bxx")
                .param("id", 2)
                .update();
        assertEquals(1, actual1);

        CudExample actual2 = jdbc.sql("select * from cud_example where id = ?")
                .param(2)
                .query(CudExample.class)
                .single();
        assertEquals(new CudExample(2, "bxx"), actual2);
    }

    @Test
    void delete() {
        int actual1 = jdbc.sql("delete cud_example where id = ?")
                .param(1)
                .update();
        assertEquals(1, actual1);

        Optional<CudExample> actual2 = jdbc.sql("select * from cud_example where id = ?")
                .param(1)
                .query(CudExample.class)
                .optional();
        assertTrue(actual2.isEmpty());
    }
}
