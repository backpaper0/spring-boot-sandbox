package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class QualifierTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class, Hoge.class, Bar1.class, Bar2.class, Bar3.class, Bar4.class,
                    Bar5.class, QuxFactory.class, Quxx.class);
            context.refresh();

            final Foo bean = context.getBean(Foo.class);
            assertThat(bean.bar1, instanceOf(Bar1.class));
            assertThat(bean.bar2, instanceOf(Bar2.class));
            assertThat(bean.bar3, instanceOf(Bar3.class));
            assertThat(bean.bar4, instanceOf(Bar4.class));
            assertThat(bean.bar5, instanceOf(Bar5.class));

            final Hoge bean2 = context.getBean(Hoge.class);
            assertThat(bean2.bar1, instanceOf(Bar1.class));
            assertThat(bean2.bar2, instanceOf(Bar2.class));
            assertThat(bean2.bar3, instanceOf(Bar3.class));
            assertThat(bean2.bar4, instanceOf(Bar4.class));
            assertThat(bean2.bar5, instanceOf(Bar5.class));

            final Quxx bean3 = context.getBean(Quxx.class);
            assertThat(bean3.qux1.value, is(1));
            assertThat(bean3.qux2.value, is(2));
            assertThat(bean3.qux3.value, is(3));
            assertThat(bean3.qux4.value, is(4));
            assertThat(bean3.qux5.value, is(5));
        }
    }

    public static class Foo {

        @Autowired
        @Qualifier("1")
        Bar bar1;

        @Autowired
        @Qualifier("2")
        Bar bar2;

        @Autowired
        @Bar3Qualifier
        Bar bar3;

        @Autowired
        @Qualifier("4")
        Bar bar4;

        @Autowired
        @Bar5Qualifier
        Bar bar5;
    }

    public static class Hoge {

        final Bar bar1;
        final Bar bar2;
        final Bar bar3;
        final Bar bar4;
        final Bar bar5;

        @Autowired
        public Hoge(@Qualifier("1") final Bar bar1,
                @Qualifier("2") final Bar bar2,
                @Bar3Qualifier final Bar bar3,
                @Qualifier("4") final Bar bar4,
                @Bar5Qualifier final Bar bar5) {
            this.bar1 = bar1;
            this.bar2 = bar2;
            this.bar3 = bar3;
            this.bar4 = bar4;
            this.bar5 = bar5;
        }
    }

    public interface Bar {
    }

    @Qualifier("1")
    public static class Bar1 implements Bar {
    }

    @Qualifier("2")
    public static class Bar2 implements Bar {
    }

    @Qualifier("3")
    public static class Bar3 implements Bar {
    }

    @Bar4Qualifier
    public static class Bar4 implements Bar {
    }

    @Bar5Qualifier
    public static class Bar5 implements Bar {
    }

    @Qualifier("3")
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Bar3Qualifier {
    }

    @Qualifier("4")
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Bar4Qualifier {
    }

    @Qualifier("5")
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Bar5Qualifier {
    }

    public static class Qux {
        final int value;

        public Qux(final int value) {
            this.value = value;
        }
    }

    public static class QuxFactory {

        @Bean
        @Qualifier("1")
        Qux qux1() {
            return new Qux(1);
        }

        @Bean
        @Qualifier("2")
        Qux qux2() {
            return new Qux(2);
        }

        @Bean
        @Qualifier("3")
        Qux qux3() {
            return new Qux(3);
        }

        @Bean
        @Bar4Qualifier
        Qux qux4() {
            return new Qux(4);
        }

        @Bean
        @Bar5Qualifier
        Qux qux5() {
            return new Qux(5);
        }
    }

    public static class Quxx {

        @Autowired
        @Qualifier("1")
        Qux qux1;

        @Autowired
        @Qualifier("2")
        Qux qux2;

        @Autowired
        @Bar3Qualifier
        Qux qux3;

        @Autowired
        @Qualifier("4")
        Qux qux4;

        @Autowired
        @Bar5Qualifier
        Qux qux5;
    }

}
