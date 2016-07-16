package study.domain;

import org.seasar.doma.Domain;

@Domain(valueType = long.class)
public class Key<ENTITY> {

    private final long value;

    public Key(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
