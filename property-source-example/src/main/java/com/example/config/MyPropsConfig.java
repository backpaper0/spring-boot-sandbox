package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/foo.properties")
@PropertySource("classpath:/bar.properties")
public class MyPropsConfig {
}
