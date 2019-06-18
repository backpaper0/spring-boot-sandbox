package com.example.many_to_one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BookM2O {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @ManyToOne
    private AuthorM2O author;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public AuthorM2O getAuthor() {
        return author;
    }

    public void setAuthor(final AuthorM2O author) {
        this.author = author;
    }
}
