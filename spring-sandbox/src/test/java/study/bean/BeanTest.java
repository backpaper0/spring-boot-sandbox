package study.bean;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class BeanTest {

    @Test
    void test() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Ddd.class);
            context.register(Hhh.class);
            context.refresh();

            final Hhh hhh = context.getBean(Hhh.class);
            hhh.assertBeans();
        }
    }

    interface Aaa {
    }

    interface Bbb extends Aaa {
    }

    static class Ccc implements Bbb {
    }

    static class Ddd extends Ccc {
    }

    @Test
    void testQualifier() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Ggg.class);
            context.register(Iii.class);
            context.register(Jjj.class);
            context.refresh();

            final Iii iii = context.getBean(Iii.class);
            iii.assertBeans();

            final Jjj jjj = context.getBean(Jjj.class);
            jjj.assertBeans();

            assertTrue(iii.aaa != jjj.aaa);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    @interface Eee {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    @interface Fff {
    }

    static class Ggg {

        @Bean
        @Eee
        Ddd eee() {
            return new Ddd();
        }

        @Bean
        @Fff
        Ddd fff() {
            return new Ddd();
        }
    }

    static class Hhh {

        Aaa aaa;
        Bbb bbb;
        Ccc ccc;
        Ddd ddd;

        Hhh(final Aaa aaa, final Bbb bbb, final Ccc ccc, final Ddd ddd) {
            this.aaa = aaa;
            this.bbb = bbb;
            this.ccc = ccc;
            this.ddd = ddd;
        }

        void assertBeans() {
            assertNotNull(aaa);
            assertNotNull(bbb);
            assertNotNull(ccc);
            assertNotNull(ddd);

            assertTrue(aaa == bbb);
            assertTrue(aaa == ccc);
            assertTrue(aaa == ddd);
        }
    }

    static class Iii extends Hhh {
        Iii(@Eee final Aaa aaa, @Eee final Bbb bbb, @Eee final Ccc ccc, @Eee final Ddd ddd) {
            super(aaa, bbb, ccc, ddd);
        }
    }

    static class Jjj extends Hhh {
        Jjj(@Fff final Aaa aaa, @Fff final Bbb bbb, @Fff final Ccc ccc, @Fff final Ddd ddd) {
            super(aaa, bbb, ccc, ddd);
        }
    }

    @Test
    void testExtends() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(Kkk1.class);
            context.register(Kkk3.class);
            context.refresh();

            final Kkk3 bean = context.getBean(Kkk3.class);

            assertTrue(bean.calledMethod1);
            assertFalse(bean.calledMethod2);
            assertTrue(bean.calledMethod3);
            assertTrue(bean.calledMethod4);
        }
    }

    static class Kkk1 {
    }

    static class Kkk2 {

        boolean calledMethod1;
        boolean calledMethod2;
        boolean calledMethod3;

        @Autowired
        public void method1(Kkk1 a) {
            calledMethod1 = true;
        }

        @Autowired
        public void method2(Kkk1 a) {
            calledMethod2 = true;
        }

        public void method3(Kkk1 a) {
            calledMethod3 = true;
        }
    }

    static class Kkk3 extends Kkk2 {

        boolean calledMethod4;

        @Override
        public void method2(Kkk1 a) {
            super.method2(a);
        }

        @Autowired
        @Override
        public void method3(Kkk1 a) {
            super.method3(a);
        }

        @Autowired
        public void method4(Kkk1 a) {
            calledMethod4 = true;
        }
    }
}
