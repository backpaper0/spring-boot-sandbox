package com.example.notgood.staticx.cantchanged;

import com.example.notgood.staticx.component.Hello;
import com.example.notgood.staticx.factory.HelloFactory;
import java.io.PrintStream;

public final class CanNotChangedObject {

    public void run(final PrintStream out) {
        final Hello hello = HelloFactory.getHello();
        final String said = hello.say("world");
        out.print(said);
    }
}
