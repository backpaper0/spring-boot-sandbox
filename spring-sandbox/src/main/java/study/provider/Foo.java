package study.provider;

import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class Foo {

    private final UUID id = UUID.randomUUID();
    private final ObjectProvider<Bar> bar1;
    private final Bar bar2;

    public Foo(final ObjectProvider<Bar> bar1, final Bar bar2) {
        this.bar1 = bar1;
        this.bar2 = bar2;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBar1Id() {
        return bar1.getObject().getId();
    }

    public UUID getBar2Id() {
        return bar2.getId();
    }

    public UUID getBar1BazId() {
        return bar1.getObject().getBazId();
    }

    public UUID getBar2BazId() {
        return bar2.getBazId();
    }
}
