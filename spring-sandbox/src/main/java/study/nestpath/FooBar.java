package study.nestpath;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooBar {
    @GetMapping
    public String foo() {
        return "foo";
    }
    @GetMapping("/bar")
    public String bar() {
        return "foo/bar";
    }
}
