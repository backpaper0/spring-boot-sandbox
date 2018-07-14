package study.customautowire;

public class Foo {

    private final String value;

    public Foo(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
