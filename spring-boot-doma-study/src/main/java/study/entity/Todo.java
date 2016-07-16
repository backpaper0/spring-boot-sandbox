package study.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import study.domain.Content;
import study.domain.Key;
import study.entity.listener.TodoListener;

@Entity(immutable = true, listener = TodoListener.class)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Key<Todo> id;
    public final Content content;
    public final LocalDateTime createdAt;
    public final LocalDateTime doneAt;

    public Todo(Key<Todo> id, Content content, LocalDateTime createdAt, LocalDateTime doneAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.doneAt = doneAt;
    }

    public Todo close(LocalDateTime doneAt) {
        return new Todo(id, content, createdAt, doneAt);
    }

    public static Todo create(Content content) {
        return new Todo(null, content, null, null);
    }
}
