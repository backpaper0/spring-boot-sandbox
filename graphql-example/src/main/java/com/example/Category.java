package com.example;

public class Category {

    private final int id;
    private final String name;

    public Category(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Category(%s: %s)", id, name);
    }
}
