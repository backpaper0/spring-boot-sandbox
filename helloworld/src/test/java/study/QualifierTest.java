package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QualifierTest {

    @Test
    public void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Foo.class, Hoge.class, Bar1.class, Bar2.class, Bar3.class, Bar4.class,
                    Bar5.class);
            context.refresh();

            Foo bean = context.getBean(Foo.class);
            assertThat(bean.bar1, instanceOf(Bar1.class));
            assertThat(bean.bar2, instanceOf(Bar2.class));
            assertThat(bean.bar3, instanceOf(Bar3.class));
            assertThat(bean.bar4, instanceOf(Bar4.class));
            assertThat(bean.bar5, instanceOf(Bar5.class));

            Hoge bean2 = context.getBean(Hoge.class);
            assertThat(bean2.bar1, instanceOf(Bar1.class));
            assertThat(bean2.bar2, instanceOf(Bar2.class));
            assertThat(bean2.bar3, instanceOf(Bar3.class));
            assertThat(bean2.bar4, instanceOf(Bar4.class));
            assertThat(bean2.bar5, instanceOf(Bar5.class));
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
        public Hoge(@Qualifier("1") Bar bar1,
                @Qualifier("2") Bar bar2,
                @Bar3Qualifier Bar bar3,
                @Qualifier("4") Bar bar4,
                @Bar5Qualifier Bar bar5) {
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
}
