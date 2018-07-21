package com.example.endpoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableIntegration
public class FilterFlow {

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
                .filter(odd())
                .channel(output())
                .get();
    }

    @Bean
    public GenericSelector<Integer> odd() {
        return a -> a % 2 == 1;
    }
}
