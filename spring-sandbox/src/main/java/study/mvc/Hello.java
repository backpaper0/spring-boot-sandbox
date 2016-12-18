package study.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Hello {
    @GetMapping
    public String say(@RequestParam String name) {
        return "[GET]Hello, " + name + "!";
    }
    @PostMapping
    public String say2(@RequestParam String name) {
        return "[POST]Hello, " + name + "!";
    }
}
