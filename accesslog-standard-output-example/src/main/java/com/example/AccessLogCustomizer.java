package com.example;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Accesslog;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessLogCustomizer
        implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

    private final ServerProperties properties;

    public AccessLogCustomizer(final ServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public void customize(final ConfigurableTomcatWebServerFactory factory) {
        final Accesslog accesslog = properties.getTomcat().getAccesslog();
        final AccessLogValve valve = new AccessLogValve();
        valve.setPattern(accesslog.getPattern());
        valve.setRequestAttributesEnabled(accesslog.isRequestAttributesEnabled());
        factory.addEngineValves(valve);
    }
}
