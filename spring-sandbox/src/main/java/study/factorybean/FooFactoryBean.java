package study.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class FooFactoryBean implements FactoryBean<Foo> {

    @Override
    public Foo getObject() throws Exception {
        return new Foo();
    }

    @Override
    public Class<?> getObjectType() {
        return Foo.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
