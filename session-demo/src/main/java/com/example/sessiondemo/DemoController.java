package com.example.sessiondemo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{key}")
public class DemoController {

    @GetMapping
    public String getSession(@PathVariable final String key, final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return (String) session.getAttribute(key);
    }

    @PostMapping
    public void setSession(@PathVariable final String key, @RequestParam final String value,
            final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }
}
