package com.example;

import javax.validation.Validator;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Validator validator;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/csrfToken").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new LoginValidationFilter(validator, "/login", LoginForm.class),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
                .failureHandler((req, res, e) -> res.setStatus(HttpStatus.UNAUTHORIZED.value()))
                .and()
                .logout()
                .logoutSuccessHandler(
                        (req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler((req, res, e) -> res.setStatus(HttpStatus.FORBIDDEN.value()));
    }
}
