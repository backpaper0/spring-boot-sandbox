package com.example.echo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@Configuration
public class EchoFlow {

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(input())
                .handle(System.out::println)
                .get();
    }
}
