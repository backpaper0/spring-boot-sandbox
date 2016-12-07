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
    public Long id;
    public String content;
    @Convert(converter = LocalDateConverter.class)
    public LocalDate deadline;
    public boolean done;
}
