package com.example.endpoints.annotation;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableIntegration
public class InboundChannelAdapterFlow {

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
                .channel(output())
                .get();
    }

    @MessageEndpoint
    public static class Counter {

        private final AtomicInteger counter = new AtomicInteger(0);

        @InboundChannelAdapter(channel = "input", poller = @Poller(fixedRate = "10"))
        public int count() {
            return counter.incrementAndGet();
        }
    }
}
