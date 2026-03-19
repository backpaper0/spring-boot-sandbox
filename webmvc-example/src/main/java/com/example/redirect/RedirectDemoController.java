package com.example.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redirect")
public class RedirectDemoController {

    @GetMapping
    @ResponseBody
    public String get() {
        return "Hello, world!";
    }

    @PostMapping
    public String post() {
        return "redirect:/redirect";
    }
}
