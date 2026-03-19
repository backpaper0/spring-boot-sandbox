package com.example.common.httpclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example.api")
@Data
public class RestTemplateProperties {

    private String baseUri;
}
