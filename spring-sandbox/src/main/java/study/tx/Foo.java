package study.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Foo {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void method1() {
        logger.info("method1");
    }

    public void method2() {
        logger.info("method2");
        throw new RuntimeException();
    }
}
