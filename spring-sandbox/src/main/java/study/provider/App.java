package study.provider;

import com.example.misc.BarImpl1;
import com.example.misc.Baz;
import com.example.misc.Foo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    @Bean
    public Foo foo() {
        return new Foo();
    }

    @Bean
    public BarImpl1 bar() {
        return new BarImpl1();
    }

    @Bean
    @Qualifier("baz1")
    public Baz baz1() {
        return new Baz("1");
    }
}
