package com.example.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.props.MyProperties;

@Component
public class MyPropsExampleRunner implements CommandLineRunner {

    private final MyProperties properties;

    public MyPropsExampleRunner(MyProperties properties) {
        this.properties = properties;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("prop1=%s%n", properties.getProp1());
        System.out.printf("prop2=%s%n", properties.getProp2());
        System.out.printf("prop3=%s%n", properties.getProp3());
        System.out.printf("list=%s%n", properties.getListProp());
    }
}
