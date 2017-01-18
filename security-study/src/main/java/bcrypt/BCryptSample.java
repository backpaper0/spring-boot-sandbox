package bcrypt;

import java.security.SecureRandom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BCryptSample {

    public static void main(String[] args) {
        SpringApplication.run(BCryptSample.class, args);
    }
}

@RestController
class HelloController {
    @GetMapping("/hello")
    String hello() {
        return "Hello, world!";
    }
}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        int strength = 10;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength, random);
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("uragami")
                .password(passwordEncoder.encode("secret"))
                .authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/hello").authenticated()
                .and().formLogin();
    }
}