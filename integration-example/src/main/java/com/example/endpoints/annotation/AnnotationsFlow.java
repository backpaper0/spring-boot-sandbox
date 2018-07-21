package com.example.endpoints.annotation;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableIntegration
public class AnnotationsFlow {

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
                .filter(annotations())
                .transform(annotations())
                .split(annotations())
                .channel(output())
                .get();
    }

    @Bean
    public Annotations annotations() {
        return new Annotations();
    }

    @MessageEndpoint
    public static class Annotations {

        @Filter
        public boolean threeChars(final String payload) {
            return payload.length() == 3;
        }

        @Transformer
        public String toUpperCase(final String payload) {
            return payload.toUpperCase();
        }

        @Splitter
        public Stream<String> headTail(final String payload) {
            return Stream.of(payload.substring(0, 1), payload.substring(1));
        }
    }
}
