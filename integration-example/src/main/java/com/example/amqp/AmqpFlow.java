package com.example.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@EnableIntegration
public class AmqpFlow {

    private final ConnectionFactory connectionFactory;
    private final AmqpTemplate amqpTemplate;

    public AmqpFlow(final ConnectionFactory connectionFactory, final AmqpTemplate amqpTemplate) {
        this.connectionFactory = connectionFactory;
        this.amqpTemplate = amqpTemplate;
    }

    @Bean
    public DirectChannel input() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel output() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow sendFlow() {
        return IntegrationFlows.from(input())
                .handle(Amqp.outboundAdapter(amqpTemplate).exchangeName("foo"))
                .get();
    }

    @Bean
    public IntegrationFlow receiveFlow() {
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "bar"))
                .channel(output())
                .get();
    }
}
