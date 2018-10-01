package com.example;

import java.util.Objects;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String text;

    public Message(final Long id, final String text) {
        this.id = id;
        this.text = text;
    }

    public Message(final String text) {
        this(null, text);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        return Objects.equals(id, other.id) && Objects.equals(text, other.text);
    }
}
