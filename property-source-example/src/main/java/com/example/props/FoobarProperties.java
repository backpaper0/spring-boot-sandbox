package com.example.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app1")
@Data
public class FoobarProperties {

    private String foobar;
    private String foo;
    private String bar;
}
