package study.stereotype;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class StereotypeStudy implements CommandLineRunner, ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(StereotypeStudy.class, args);
    }

    private ApplicationContext ac;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ac.getBean(Hello.class));
        System.out.println(ac.getBean(Hello.class));
        System.out.println(ac.getBean(Hello.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }
}
