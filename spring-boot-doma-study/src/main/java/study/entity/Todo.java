package study.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import study.entity.listener.TodoListener;

@Entity(listener = TodoListener.class)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String content;
    public LocalDateTime createdAt;
    public LocalDateTime doneAt;
}
