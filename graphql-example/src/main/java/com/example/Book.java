package com.example;

public class Book {

    private final int id;
    private final String title;
    private final int authorId;

    public Book(final int id, final String title, final int authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }
}
