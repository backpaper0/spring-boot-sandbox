package com.example;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class BbsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BbsApplication.class, args);
    }
}

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("hoge").password("hoge").roles("USER")
                .and()
                .withUser("foobar").password("foobar").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("USER", "ADMIN");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/post").authenticated()
                .and()
                .formLogin();
    }
}

@Controller
class BbsController {

    final List<Post> posts = Collections.synchronizedList(new ArrayList<>());

    public BbsController() {
        posts.add(new Post("hoge", "ほげほげ〜"));
        posts.add(new Post("foobar", "フバフバ〜"));
    }

    @GetMapping("/")
    String home(Principal principal, Model model) {
        model.addAttribute("posts", posts);
        model.addAttribute("principal", principal);
        return "bbs";
    }

    @PostMapping("/post")
    String post(Principal principal, Model model, @RequestParam String content) {
        posts.add(new Post(principal.getName(), content));
        return "redirect:/";
    }
}

class Post {
    public final String user;
    public final String content;
    public Post(String user, String content) {
        this.user = user;
        this.content = content;
    }
}