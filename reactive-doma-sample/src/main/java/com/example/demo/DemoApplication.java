package com.example.demo;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements InitializingBean {

    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    private final DemoDao dao;

    public DemoApplication(final DemoDao dao) {
        this.dao = Objects.requireNonNull(dao);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dao.setUp();
    }
}
