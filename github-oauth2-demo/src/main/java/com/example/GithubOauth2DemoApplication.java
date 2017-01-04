package com.example;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class GithubOauth2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubOauth2DemoApplication.class, args);
    }

    @GetMapping("/")
    Principal hello(Principal principal) {
        return principal;
    }
}
