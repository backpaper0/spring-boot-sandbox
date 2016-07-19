package helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    public HelloWorld() {
        new Throwable().printStackTrace(System.out);
    }

    @RequestMapping("/hello")
    public String sayHello() {
        new Throwable().printStackTrace(System.out);
        return "Hello world!";
    }
}
