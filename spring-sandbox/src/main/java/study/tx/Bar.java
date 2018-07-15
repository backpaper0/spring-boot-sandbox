package study.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Bar {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Foo foo;

    public Bar(final Foo foo) {
        this.foo = foo;
    }

    public void method1() {
        logger.info("begin method1");
        foo.method1();
        logger.info("end method1");
    }

    public void method2() {
        logger.info("begin method2");
        foo.method2();
        logger.info("end method2");
    }
}
