package study.basic;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class Study implements CommandLineRunner, ApplicationContextAware {

    public static void main(final String[] args) {
        SpringApplication.run(Study.class, args);
    }

    private ApplicationContext ac;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println(ac.getBean(Hello.class));
        System.out.println(ac.getBean(Hello.class));
        System.out.println(ac.getBean(Hello.class));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.ac = applicationContext;
    }
}
