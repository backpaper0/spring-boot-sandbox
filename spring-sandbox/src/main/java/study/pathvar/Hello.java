package study.pathvar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @GetMapping("/hello/{name}")
    public String get(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/hoge/{suffix}")
    public String suffix(@PathVariable String suffix) {
        return suffix;
    }
}
