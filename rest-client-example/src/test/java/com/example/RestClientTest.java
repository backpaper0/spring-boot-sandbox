package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import com.example.entity.Foo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestClientTest {

    @Autowired
    RestClient rest;
    @LocalServerPort
    int port;

    @Test
    void findOne() {
        ResponseEntity<Foo> actual = rest.get()
                .uri("http://localhost:{port}/foos/1", port)
                .retrieve()
                .toEntity(Foo.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(new Foo(1, "fxx"), actual.getBody());
    }

    @Test
    void findList() {
        ResponseEntity<List<Foo>> actual = rest.get()
                .uri("http://localhost:{port}/foos", port)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Foo>>() {
                });

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(List.of(new Foo(1, "fxx"), new Foo(2, "bar")), actual.getBody());
    }

    @Test
    void postJson() {
        ResponseEntity<Foo> actual = rest.post()
                .uri("http://localhost:{port}/foos/3", port)
                .body(new Foo(3, "baz"))
                .retrieve()
                .toEntity(Foo.class);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(new Foo(3, "baz"), actual.getBody());
    }
}
