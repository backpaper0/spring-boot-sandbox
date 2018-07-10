package com.example.multitm;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MultiTransactionManagerConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "foo.datasource")
    public DataSourceProperties fooDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fooDataSource() {
        return fooDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager fooTransactionManager() {
        return new DataSourceTransactionManager(fooDataSource());
    }

    @Bean
    @ConfigurationProperties(prefix = "bar.datasource")
    public DataSourceProperties barDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource barDataSource() {
        return barDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager barTransactionManager() {
        return new DataSourceTransactionManager(barDataSource());
    }

    @Bean
    @Primary
    public DataSourceProperties fakeDataSourceProperties() {
        return new DataSourceProperties();
    }
}
