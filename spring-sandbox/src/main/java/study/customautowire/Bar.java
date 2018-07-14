package study.customautowire;

import org.springframework.stereotype.Component;

@Component
public class Bar {

    private final Foo foo1;
    private final Foo foo2;

    public Bar(final Foo foo1, @MyQualifier final Foo foo2) {
        this.foo1 = foo1;
        this.foo2 = foo2;
    }

    public void run() {
        System.out.println(foo1);
        System.out.println(foo2);
    }
}
