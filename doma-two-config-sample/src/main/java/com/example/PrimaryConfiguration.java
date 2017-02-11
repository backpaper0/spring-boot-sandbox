package com.example;

import javax.sql.DataSource;
import org.seasar.doma.boot.autoconfigure.DomaConfig;
import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder;
import org.seasar.doma.boot.autoconfigure.DomaProperties;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.EntityListenerProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PrimaryConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("sample.datasource.primary")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties("sample.doma.primary")
    DomaProperties domaProperties() {
        return new DomaProperties();
    }

    @Bean
    @Primary
    Config config(EntityListenerProvider entityListenerProvider) {
        DomaConfigBuilder builder = new DomaConfigBuilder()
                .dataSource(dataSource())
                .dialect(domaProperties().getDialect().create())
                .sqlFileRepository(domaProperties().getSqlFileRepository().create())
                .naming(domaProperties().getNaming().naming())
                .entityListenerProvider(entityListenerProvider);
        return new DomaConfig(builder);
    }
}
