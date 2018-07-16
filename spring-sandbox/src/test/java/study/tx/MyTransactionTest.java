package study.tx;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.IllegalTransactionStateException;

public class MyTransactionTest {

    @Test
    public void method1() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(MyTransactionConfig.class);
            context.refresh();

            final Bar bar = context.getBean(Bar.class);
            bar.method1();
        }
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void method2() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(MyTransactionConfig.class);
            context.refresh();

            final Bar bar = context.getBean(Bar.class);
            bar.method2();
        }
    }

    @Test
    public void method3() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(MyTransactionConfig.class);
            context.refresh();

            final Bar bar = context.getBean(Bar.class);
            bar.method3();
        }
    }
}
