package study.proxy;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Foo {
    private static final AtomicInteger counter = new AtomicInteger();
    private final int count = counter.incrementAndGet();

    public String doMethod() {
        return "Foo(" + count + ")";
    }
}
