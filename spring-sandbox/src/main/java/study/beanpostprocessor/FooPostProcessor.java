package study.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class FooPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName)
            throws BeansException {
        if (bean instanceof Foo) {
            return new FooWrapper((Foo) bean, "B");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName)
            throws BeansException {
        if (bean instanceof Foo) {
            return new FooWrapper((Foo) bean, "A");
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private static class FooWrapper extends Foo {

        private final Foo foo;
        private final String added;

        public FooWrapper(final Foo foo, final String added) {
            this.foo = foo;
            this.added = added;
        }

        @Override
        public String get() {
            return String.format("%2$s%1$s%2$s", foo.get(), added);
        }
    }
}
