package com.example;

import javax.sql.DataSource;

import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder;
import org.seasar.doma.boot.autoconfigure.DomaProperties;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.EntityListenerProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondaryConfiguration {

    @Bean
    @Secondary
    @ConfigurationProperties("sample.datasource.secondary")
    DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Secondary
    DataSource secondaryDataSource() {
        return secondaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Secondary
    @ConfigurationProperties("sample.doma.secondary")
    DomaProperties secondaryDomaProperties() {
        return new DomaProperties();
    }

    @Bean
    @Secondary
    Config secondaryConfig(final EntityListenerProvider entityListenerProvider) {
        final DomaConfigBuilder builder = secondaryDomaProperties()
                .initializeDomaConfigBuilder()
                .dataSource(secondaryDataSource())
                .entityListenerProvider(entityListenerProvider);
        return builder.build(secondaryDomaProperties());
    }
}
