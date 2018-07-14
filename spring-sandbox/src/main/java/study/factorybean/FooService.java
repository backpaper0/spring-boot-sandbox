package study.factorybean;

import org.springframework.stereotype.Component;

@Component
public class FooService {

    private final Foo foo1;
    private final Foo foo2;
    private final Foo foo3;

    public FooService(final Foo foo1, final Foo foo2, final Foo foo3) {
        this.foo1 = foo1;
        this.foo2 = foo2;
        this.foo3 = foo3;
    }

    public void run() {
        System.out.println(foo1);
        System.out.println(foo2);
        System.out.println(foo3);
    }
}
