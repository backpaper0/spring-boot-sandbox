package com.example.endpoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
@EnableIntegration
public class AggregatorFlow {

    @Bean
    public MessageChannel input1() {
        return new DirectChannel();
    }

    @Bean
    public PollableChannel output1() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow flow1() {
        return IntegrationFlows.from(input1())
                .aggregate()
                .channel(output1())
                .get();
    }

    @Bean
    public MessageChannel input2() {
        return new DirectChannel();
    }

    @Bean
    public PollableChannel output2() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow flow2() {
        return IntegrationFlows.from(input2())
                .aggregate(a -> a.correlationStrategy(correlationStrategy())
                        .releaseStrategy(releaseStrategy()))
                .channel(output2())
                .get();
    }

    @Bean
    public CorrelationStrategy correlationStrategy() {
        return message -> {
            final int payload = (Integer) message.getPayload();
            return payload % 4;
        };
    }

    @Bean
    public ReleaseStrategy releaseStrategy() {
        return group -> group.size() == 3;
    }
}
