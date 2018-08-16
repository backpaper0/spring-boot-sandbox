package com.example.notgood.staticx.factory;

import com.example.notgood.staticx.component.Hello;

public final class HelloFactory {

    private static Hello hello;

    private HelloFactory() {
        //nop
    }

    public static void setHello(final Hello hello) {
        HelloFactory.hello = hello;
    }

    public static Hello getHello() {
        return hello;
    }
}
