package app;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Batchを使うときに必要になる
@EnableBatchProcessing
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
