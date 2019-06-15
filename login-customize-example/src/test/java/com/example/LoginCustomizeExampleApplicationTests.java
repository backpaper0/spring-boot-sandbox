package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginCustomizeExampleApplicationTests {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void contextLoads() {

        assertEquals(HttpStatus.UNAUTHORIZED,
                template.getForEntity("/hello", String.class).getStatusCode());

        final RequestEntity<?> request = RequestEntity.get(URI.create("/hello"))
                .header("Authorization", "Bearer xxx").build();
        final ResponseEntity<String> response = template.exchange(request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("hello testuser", response.getBody());
    }
}
