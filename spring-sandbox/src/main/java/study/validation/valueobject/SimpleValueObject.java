package study.validation.valueobject;

import java.util.Objects;

public final class SimpleValueObject {

    private final String value;

    public SimpleValueObject(final String value) {
        Objects.requireNonNull(value);
        if (value.length() > 2) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
