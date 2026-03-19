package com.example.error;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyErrorControllerTest {

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
    void notFound() {
        var response = http.getForEntity("http://localhost:" + port + "/no-exists-path", Map.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Map.of("message", "Not Found"), response.getBody());
    }

    @Test
    void serverError() {
        var response = http.getForEntity("http://localhost:" + port + "/songs/simulate-bug", Map.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Map.of("message", "Internal Server Error"), response.getBody());
    }

    @Test
    void methodNotAllow() {
        var response = http.postForEntity(
                "http://localhost:" + port + "/songs/simulate-bug", Map.of("data", "dummy"), Map.class);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals(Map.of("message", "Method Not Allowed"), response.getBody());
    }
}
