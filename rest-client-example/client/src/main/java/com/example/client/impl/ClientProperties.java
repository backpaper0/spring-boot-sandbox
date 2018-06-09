package com.example.client.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example.client")
public class ClientProperties {

    private String baseUri;
    private String name;
    private String password;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
