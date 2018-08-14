package study;

import static org.assertj.core.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSample {

    @Test
    public void test() throws Exception {
        try (final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(BarImpl.class);
            context.register(Foo.class);
            context.refresh();

            final Foo foo = context.getBean(Foo.class);

            assertThat(foo.bar.get()).isEqualTo("Hello World");
        }
    }

    static class Foo {

        private final Bar bar;

        public Foo(final Bar bar) {
            this.bar = Objects.requireNonNull(bar);
        }
    }

    interface Bar {
        String get();
    }

    static class BarImpl implements Bar {

        @Override
        public String get() {
            return "Hello World";
        }
    }
}
