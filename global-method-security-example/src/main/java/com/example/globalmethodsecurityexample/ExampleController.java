package com.example.globalmethodsecurityexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @CustomAuthorize(Roles.FOO)
    @GetMapping("/foo")
    String getFoo() {
        return "foo";
    }

    @CustomAuthorize({ Roles.FOO, Roles.BAR })
    @GetMapping("/bar")
    String getBar() {
        return "bar";
    }

    @GetMapping("/baz")
    String getBaz() {
        return "baz";
    }
}
