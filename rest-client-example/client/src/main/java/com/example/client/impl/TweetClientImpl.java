package com.example.client.impl;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.client.Tweet;
import com.example.client.TweetClient;

@Component
public class TweetClientImpl implements TweetClient {

    private final ClientProperties clientProperties;
    private final RestTemplate restTemplate;

    private final AtomicReference<String> token = new AtomicReference<>();

    public TweetClientImpl(final ClientProperties clientProperties,
            final RestTemplate restTemplate) {
        this.clientProperties = Objects.requireNonNull(clientProperties);
        this.restTemplate = Objects.requireNonNull(restTemplate);
    }

    @Override
    public List<Tweet> getTweets() {
        while (true) {
            if (token.get() == null) {
                authenticate();
            }
            final RequestEntity<?> requestEntity = RequestEntity
                    .get(URI.create("http://localhost:8888/tweets"))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.get())
                    .accept(MediaType.APPLICATION_JSON)
                    .build();
            final ParameterizedTypeReference<List<Tweet>> responseType = new ParameterizedTypeReference<List<Tweet>>() {
            };
            try {
                final ResponseEntity<List<Tweet>> responseEntity = restTemplate.exchange(
                        requestEntity,
                        responseType);
                return responseEntity.getBody();
            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() != HttpStatus.FORBIDDEN) {
                    throw e;
                }
            }
            authenticate();
        }
    }

    @Override
    public void postTweet(final String text) {
        while (true) {
            if (token.get() == null) {
                authenticate();
            }
            final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("text", text);
            final RequestEntity<?> requestEntity = RequestEntity
                    .post(URI.create("http://localhost:8888/tweets"))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.get())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body);
            try {
                restTemplate.exchange(requestEntity, Void.class);
                return;

            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() != HttpStatus.FORBIDDEN) {
                    throw e;
                }
            }
            authenticate();
        }
    }

    private void authenticate() {
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", clientProperties.getName());
        body.add("password", clientProperties.getPassword());
        final RequestEntity<?> requestEntity = RequestEntity
                .post(URI.create("http://localhost:8888/token"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_PLAIN)
                .body(body);
        final ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity,
                String.class);
        token.set(responseEntity.getBody());
    }
}
