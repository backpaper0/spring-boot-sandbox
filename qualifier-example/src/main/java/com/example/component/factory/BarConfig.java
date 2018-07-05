package com.example.component.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.annotation.Tertiary;

@Configuration
public class BarConfig {

    @Bean
    @Primary
    public Bar primaryBar() {
        return new DefaultBar("Primary bar");
    }

    @Bean
    @Qualifier("secondary")
    public Bar secondaryBar() {
        return new DefaultBar("Secondary bar");
    }

    @Bean
    @Tertiary
    public Bar tertiaryBar() {
        return new DefaultBar("Tertiary bar");
    }
}
