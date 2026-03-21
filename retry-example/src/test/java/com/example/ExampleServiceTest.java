package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.ExampleService.ExampleContext;
import com.example.ExampleService.ExampleException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExampleServiceTest {

    @Autowired
    ExampleService sut;

    @Test
    void リトライで成功する() {
        assertEquals("test1(4/4)", sut.demo(new ExampleContext("test1", 4)));
    }

    @Test
    void リトライ上限数を超えて失敗する() {
        assertThrows(ExampleException.class, () -> sut.demo(new ExampleContext("test2", 100)));
    }
}
