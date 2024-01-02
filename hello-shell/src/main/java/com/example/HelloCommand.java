package com.example;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class HelloCommand {

    @ShellMethod(key = "hello", value = "Say hello.")
    public String sayHello(@ShellOption(defaultValue = "World") String name) {
        return "Hello " + name;
    }
}
