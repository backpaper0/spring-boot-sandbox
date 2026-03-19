package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example")
@Data
public class ExampleBatchProperties {

    private int chunkSize;
    private Integer errorId;
}
