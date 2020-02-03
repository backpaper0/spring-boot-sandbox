package com.example;

public class Author {

    private final int id;
    private final String name;

    public Author(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
