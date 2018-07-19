package com.example.echo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class EchoFlow {

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(input())
                .handle(output())
                .get();
    }

    @Bean
    public MessageHandler output() {
        final CharacterStreamWritingMessageHandler messageHandler = CharacterStreamWritingMessageHandler
                .stdout();
        messageHandler.setShouldAppendNewLine(true);
        return messageHandler;
    }
}
