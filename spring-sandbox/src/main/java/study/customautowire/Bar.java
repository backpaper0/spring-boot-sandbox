package study.customautowire;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class Bar {

    private final Foo foo1;
    private final Foo foo2;

    public Bar(final Foo foo1, @MyQualifier final Foo foo2) {
        this.foo1 = foo1;
        this.foo2 = foo2;
    }

    public List<String> get() {
        return Stream.of(foo1, foo2).map(Objects::toString).collect(Collectors.toList());
    }
}
