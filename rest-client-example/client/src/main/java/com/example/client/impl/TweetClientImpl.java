package com.example.client.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.client.Tweet;
import com.example.client.TweetClient;

@Component
public class TweetClientImpl implements TweetClient {

    private final HttpExecutor httpExecutor;

    public TweetClientImpl(final HttpExecutor httpExecutor) {
        this.httpExecutor = Objects.requireNonNull(httpExecutor);
    }

    @Override
    public List<Tweet> getTweets() {
        return httpExecutor.execute((restTemplate, uriComponentsBuilder) -> {
            final RequestEntity<?> requestEntity = RequestEntity
                    .get(uriComponentsBuilder.path("/tweets").build().toUri())
                    .accept(MediaType.APPLICATION_JSON)
                    .build();
            final ParameterizedTypeReference<List<Tweet>> responseType = new ParameterizedTypeReference<List<Tweet>>() {
            };
            final ResponseEntity<List<Tweet>> responseEntity = restTemplate.exchange(
                    requestEntity,
                    responseType);
            return responseEntity.getBody();
        });
    }

    @Override
    public void postTweet(final String text) {
        httpExecutor.execute((restTemplate, uriComponentsBuilder) -> {
            final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("text", text);
            final RequestEntity<?> requestEntity = RequestEntity
                    .post(uriComponentsBuilder.path("/tweets").build().toUri())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body);
            restTemplate.exchange(requestEntity, Void.class);
            return null;
        });
    }
}
