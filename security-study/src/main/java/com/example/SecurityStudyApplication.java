package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityStudyApplication.class, args);
    }
}

@RestController
class SampleController {

    @GetMapping("/foo")
    String foo() {
        return "foo";
    }

    @GetMapping("/bar")
    String bar() {
        return "bar";
    }

    @GetMapping("/baz")
    String baz() {
        return "baz";
    }
}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bar").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}