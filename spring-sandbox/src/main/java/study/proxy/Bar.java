package study.proxy;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Bar {
    private static final AtomicInteger counter = new AtomicInteger();
    private final int count = counter.incrementAndGet();
    private final Foo foo;

    public Bar(final Foo foo) {
        this.foo = Objects.requireNonNull(foo);
    }

    @GetMapping("/sample")
    public String doMethod() {
        return "Bar(" + count + ") " + foo + " " + System.identityHashCode(foo) + " "
                + foo.doMethod();
    }
}
