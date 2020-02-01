package com.example.webmvc.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ModelExampleApplicationTest {

    @Autowired
    TestRestTemplate http;

    @Test
    void home() throws Exception {
        final ModelExampleModel model = http.getForObject("/", ModelExampleModel.class);
        assertEquals(new ModelExampleModel("default"), model);
    }
}
