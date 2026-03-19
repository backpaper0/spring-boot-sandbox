package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SongControllerWithRestTemplateTest extends RestControllerTestBase {

    @LocalServerPort
    int port;

    RestTemplate http;

    @BeforeEach
    void setup() {
        http = new RestTemplate();
        http.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }
        });
    }

    @Test
    void testGetList() throws Exception {
        var request = RequestEntity.get("http://localhost:" + port + "/songs").build();

        var response = http.exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(read("expected-list.json"), response.getBody(), false);
    }

    @Test
    void testGetListWithCriteria() throws Exception {
        var uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/songs")
                .queryParam("singer", "1")
                .build()
                .toUri();

        var request = RequestEntity.get(uri).build();

        var response = http.exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(read("expected-list-with-criteria.json"), response.getBody(), false);
    }

    @Test
    void testGetListWithInvalidCriteria() throws Exception {
        var uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/songs")
                .queryParam("singer", "0")
                .build()
                .toUri();

        var request = RequestEntity.get(uri).build();

        var response = http.exchange(request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        JSONAssert.assertEquals(read("expected-list-with-invalid-criteria.json"), response.getBody(), false);
    }

    @Test
    void testGetListNoExistsSinger() throws Exception {
        var uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/songs")
                .queryParam("singer", "999")
                .build()
                .toUri();

        var request = RequestEntity.get(uri).build();

        var response = http.exchange(request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(read("expected-list-empty.json"), response.getBody(), false);
    }

    @Test
    void testCreate() throws Exception {
        var request = RequestEntity.post("http://localhost:" + port + "/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .body(read("create.json"));

        var response = http.exchange(request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        JSONAssert.assertEquals(read("expected-create.json"), response.getBody(), false);
    }

    @Test
    void testCreateWithoutExpectedId() throws Exception {
        var request = RequestEntity.post("http://localhost:" + port + "/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .body(read("create.json"));

        var response = http.exchange(request, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // strictがfalseなら期待値側に無いフィールドは無視される
        JSONAssert.assertEquals(read("expected-create-without-id.json"), response.getBody(), false);
    }
}
