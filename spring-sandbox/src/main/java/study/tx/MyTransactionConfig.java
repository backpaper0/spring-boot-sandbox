package study.tx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;

@Configuration
@Import(ProxyTransactionManagementConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class MyTransactionConfig {

    @Bean
    public MyTransactionManager myTransactionManager() {
        return new MyTransactionManager();
    }

    @Bean
    public Foo foo() {
        return new Foo();
    }

    @Bean
    public Bar bar() {
        return new Bar(foo());
    }
}
