package study.entity.listener;

import java.time.Clock;
import java.time.LocalDateTime;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import study.entity.Todo;

@Component
public class TodoListener implements EntityListener<Todo> {

    private final Clock clock;

    @Autowired
    public TodoListener(Clock clock) {
        this.clock = clock;
    }

    //引数なしコンストラクタが無いとDomaのコンパイル時チェックに引っかかる
    public TodoListener() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void preInsert(Todo entity, PreInsertContext<Todo> context) {
        Todo newEntity = new Todo(null, entity.content, LocalDateTime.now(clock), entity.doneAt);
        context.setNewEntity(newEntity);
    }
}
