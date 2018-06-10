package com.example.client.impl;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HttpExecutor {

    private final RestTemplate restTemplateForExecution = new RestTemplate();
    private final RestTemplate restTemplateForAuthentication = new RestTemplate();

    private final AtomicReference<String> tokenHolder = new AtomicReference<>();

    private final ClientProperties clientProperties;

    public HttpExecutor(final ClientProperties clientProperties) {
        this.clientProperties = clientProperties;

        final ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            final String token = tokenHolder.get();
            if (token != null) {
                request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
            return execution.execute(request, body);
        };
        this.restTemplateForExecution.setInterceptors(Collections.singletonList(interceptor));
    }

    public <T> T execute(final BiFunction<RestTemplate, UriComponentsBuilder, T> function) {
        while (true) {
            if (tokenHolder.get() == null) {
                authenticate();
            }
            try {
                return function.apply(restTemplateForExecution, uriComponentsBuilder());

            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() != HttpStatus.FORBIDDEN) {
                    throw e;
                }
            }
            authenticate();
        }
    }

    private UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder
                .fromHttpUrl(clientProperties.getBaseUri());
    }

    private void authenticate() {
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", clientProperties.getName());
        body.add("password", clientProperties.getPassword());
        final RequestEntity<?> requestEntity = RequestEntity
                .post(uriComponentsBuilder().path("/token").build().toUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_PLAIN)
                .body(body);
        final ResponseEntity<String> responseEntity = restTemplateForAuthentication.exchange(
                requestEntity,
                String.class);
        tokenHolder.set(responseEntity.getBody());
    }
}
