package study.customautowire;

import java.util.Collections;

import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CustomAutowireConfigurerExample {

    @Bean
    public CustomAutowireConfigurer customAutowireConfigurer() {
        final CustomAutowireConfigurer configurer = new CustomAutowireConfigurer();
        configurer.setCustomQualifierTypes(Collections.singleton(MyQualifier.class));
        return configurer;
    }

    @Bean
    public Foo foo1() {
        return new Foo("foo 1");
    }

    @Bean
    @MyQualifier
    public Foo foo2() {
        return new Foo("foo 2");
    }
}
