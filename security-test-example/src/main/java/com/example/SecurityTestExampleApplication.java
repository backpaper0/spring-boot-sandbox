package com.example;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityTestExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SecurityTestExampleApplication.class, args);
    }
}

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/foo").permitAll()
                .antMatchers("/bar").hasRole("BAR")
                .antMatchers("/baz").hasAuthority("ROLE_BAZ")
                .anyRequest().authenticated()

                .and().httpBasic()

                .and().csrf().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        final Supplier<UserBuilder> builder = () -> User.builder()
                .passwordEncoder(
                        PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

        auth.inMemoryAuthentication()
                .withUser(builder.get().username("foo").password("foo").roles("FOO"))
                .withUser(builder.get().username("bar").password("bar").roles("BAR"))
                .withUser(builder.get().username("baz").password("baz").roles("BAZ"))
                .withUser(builder.get().username("qux").password("qux").roles("QUX"));
    }
}

@RestController
class ExampleController {

    private final ExampleService exampleService;

    public ExampleController(final ExampleService exampleService) {
        this.exampleService = Objects.requireNonNull(exampleService);
    }

    @GetMapping("/foo")
    String getFoo(final Authentication a) {
        return exampleService.get(a, "foo");
    }

    @GetMapping("/bar")
    String getBar(final Authentication a) {
        return exampleService.get(a, "bar");
    }

    @GetMapping("/baz")
    String getBaz(final Authentication a) {
        return exampleService.get(a, "baz");
    }

    @GetMapping("/qux")
    String getQux(final Authentication a) {
        return exampleService.get(a, "qux");
    }
}

@Service
class ExampleService {
    String get(final Authentication a, final String s) {
        return Optional.ofNullable(a).map(Authentication::getName).orElse("") + ":" + s;
    }
}