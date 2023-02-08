package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import com.example.session.LoginUserInfo;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

	@Bean
	DefaultHttpSecurityExpressionHandler expressionHandler() {
		return new DefaultHttpSecurityExpressionHandler();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, LoginUserInfo loginUserInfo,
			DefaultHttpSecurityExpressionHandler expressionHandler)
			throws Exception {

		WebExpressionAuthorizationManager passedTwoFactorAuth = new WebExpressionAuthorizationManager(
				"isAuthenticated() and @loginUserInfo.isPassedTwoFactorAuth()");
		passedTwoFactorAuth.setExpressionHandler(expressionHandler);

		return http
				.authorizeHttpRequests(c -> c

						// ワンタイムパスワード入力画面はログイン認証していないとアクセスできない
						.requestMatchers("/oneTimePassword").authenticated()

						// ホーム画面はログイン認証に加え、2要素認証を通過していないとアクセスできない
						.requestMatchers("/home").access(passedTwoFactorAuth)

						.anyRequest().authenticated())

				.formLogin(
						c -> c.successHandler(new AuthenticationSuccessHandlerImpl(loginUserInfo)))

				.build();
	}
}
