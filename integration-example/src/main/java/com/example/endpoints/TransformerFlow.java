package com.example.endpoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.transformer.GenericTransformer;

@Configuration
@EnableIntegration
public class TransformerFlow {

    @Bean
    public DirectChannel input() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel output() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(input())
                .transform(upperCase())
                .channel(output())
                .get();
    }

    @Bean
    public GenericTransformer<String, String> upperCase() {
        return String::toUpperCase;
    }
}
