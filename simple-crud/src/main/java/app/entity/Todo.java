package app.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Todo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate deadline;
    private boolean done;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }
}
