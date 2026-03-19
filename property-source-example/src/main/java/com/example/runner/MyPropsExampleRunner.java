package com.example.runner;

import com.example.props.FoobarProperties;
import com.example.props.MyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyPropsExampleRunner implements CommandLineRunner {

    private final MyProperties myProperties;
    private final FoobarProperties foobarProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(myProperties);
        System.out.println(foobarProperties);
    }
}
