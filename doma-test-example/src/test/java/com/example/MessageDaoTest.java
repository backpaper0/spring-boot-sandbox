package com.example;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.seasar.doma.jdbc.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageDaoTest {

    @Autowired
    private MessageDao dao;

    @Test
    public void insert() {
        assertEquals(
                Collections.singletonList(new Message(1L, "Hello World")),
                dao.selectAll());

        final Result<Message> result = dao.insert(new Message("foobar"));

        assertEquals(1, result.getCount());
        assertEquals(
                Arrays.asList(new Message(1L, "Hello World"), new Message(2L, "foobar")),
                dao.selectAll());
    }

    @Test
    public void selectAll() {
        assertEquals(
                Collections.singletonList(new Message(1L, "Hello World")),
                dao.selectAll());
    }
}
