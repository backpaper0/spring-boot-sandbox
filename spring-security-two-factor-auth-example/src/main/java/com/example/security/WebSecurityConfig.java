package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.session.LoginUserInfo;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, LoginUserInfo loginUserInfo)
			throws Exception {

		return http
				.authorizeRequests(c -> c

						// ワンタイムパスワード入力画面はログイン認証していないとアクセスできない
						.mvcMatchers("/oneTimePassword").authenticated()

						// ホーム画面はログイン認証に加え、2要素認証を通過していないとアクセスできない
						.mvcMatchers("/home").access("isAuthenticated() and @loginUserInfo.isPassedTwoFactorAuth()")

						.anyRequest().authenticated())

				.formLogin(
						c -> c.successHandler(new AuthenticationSuccessHandlerImpl(loginUserInfo)))

				.build();
	}
}
