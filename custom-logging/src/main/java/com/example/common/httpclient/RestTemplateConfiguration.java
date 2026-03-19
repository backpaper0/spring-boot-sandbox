package com.example.common.httpclient;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private RestTemplateProperties properties;

    @Autowired
    private RoundTripTimeRecorder interceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        DefaultUriBuilderFactory handler = new DefaultUriBuilderFactory(properties.getBaseUri());
        handler.setEncodingMode(EncodingMode.URI_COMPONENT);
        restTemplate.setUriTemplateHandler(handler);
        restTemplate.setInterceptors(List.of(interceptor));
        return restTemplate;
    }
}
