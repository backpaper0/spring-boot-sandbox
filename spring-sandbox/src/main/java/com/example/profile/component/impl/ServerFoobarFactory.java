package com.example.profile.component.impl;

import com.example.profile.annotation.ServerEnv;
import com.example.profile.component.Foobar;
import com.example.profile.component.FoobarFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEnv
public class ServerFoobarFactory implements FoobarFactory {

    @Override
    public Foobar create(String name) {
        return () -> name + ":server";
    }
}
