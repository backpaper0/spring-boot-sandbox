package study.beanpostprocessor;

import com.example.misc.Baz;
import com.example.misc.Foo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    @Bean
    public Foo foo() {
        return new Foo();
    }

    @Bean
    public Baz bar(Foo foo) {
        return new Baz(foo.toString());
    }
}
