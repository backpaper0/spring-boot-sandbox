package study.provider;

import java.util.UUID;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Bar {

    private final UUID id = UUID.randomUUID();
    private final Baz baz;

    public Bar(final Baz baz) {
        this.baz = baz;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBazId() {
        return baz.getId();
    }
}