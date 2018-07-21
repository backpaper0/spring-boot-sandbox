package com.example.channel2channel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableIntegration
public class Channel2ChannelFlow {

    @Bean
    public DirectChannel input() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel inOut() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel output() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow flow1() {
        return IntegrationFlows.from(input())
                .channel(inOut())
                .get();
    }

    @Bean
    public IntegrationFlow flow2() {
        return IntegrationFlows.from(inOut())
                .channel(output())
                .get();
    }
}
