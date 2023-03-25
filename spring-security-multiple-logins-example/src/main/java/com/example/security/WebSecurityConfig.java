package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
		return securityFilterChainInternal(http, userDetailsService1(), "protected1");
	}

	@Bean
	SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
		return securityFilterChainInternal(http, userDetailsService2(), "protected2");
	}

	@Bean
	UserDetailsService userDetailsService1() {
		return userDetailsServiceInternal("alice");
	}

	@Bean
	UserDetailsService userDetailsService2() {
		return userDetailsServiceInternal("bob");
	}

	private UserDetailsService userDetailsServiceInternal(String username) {
		UserDetails user = User.withUsername(username).password("pass").roles("USER")
				.passwordEncoder(passwordEncoder()::encode).build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private static SecurityFilterChain securityFilterChainInternal(HttpSecurity http,
			UserDetailsService userDetailsService, String name) throws Exception {
		return http
				// SecurityFilterChainを適用するリクエストのマッチャーを定義する。
				.securityMatcher("/" + name + "/**")

				.authorizeHttpRequests(customizer -> customizer
						.anyRequest().authenticated())

				.formLogin(customizer -> customizer
						.loginPage("/" + name + "/login")
						.loginProcessingUrl("/" + name + "/login")
						// loginPage、loginProcessingUrlで設定したパスへのアクセスを許可する。
						// これをしないとauthorizeHttpRequestsで明示的に設定する必要がある。
						.permitAll())

				.requestCache(customizer -> {
					// RequestCacheはログイン成功時、認証前にアクセスしようとしていたページを開くなどに使用される。
					// SecurityFilterChain毎に異なる属性名を設定する必要がある。
					HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
					requestCache.setSessionAttrName("SPRING_SECURITY_SAVED_REQUEST." + name);

					customizer.requestCache(requestCache);
				})

				.logout(customizer -> customizer
						.logoutUrl("/" + name + "/logout")
						// SecurityFilterChainは2つだけどHTTPセッションは1つなので、ログアウト時に無効化しないようにする。
						.invalidateHttpSession(false))

				.securityContext(customizer -> {
					// SecurityContextを保存する際に使用する属性名もSecurityFilterChain毎に異なるものにする必要がある。

					HttpSessionSecurityContextRepository delegate1 = new HttpSessionSecurityContextRepository();
					delegate1.setSpringSecurityContextKey(
							HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY + "." + name);

					RequestAttributeSecurityContextRepository delegate2 = new RequestAttributeSecurityContextRepository(
							RequestAttributeSecurityContextRepository.DEFAULT_REQUEST_ATTR_NAME + "." + name);

					DelegatingSecurityContextRepository securityContextRepository = new DelegatingSecurityContextRepository(
							delegate2, delegate1);

					customizer.securityContextRepository(securityContextRepository);
				})

				.userDetailsService(userDetailsService)

				.csrf(customizer -> {
					// CSRFトークンをHTTPセッションに保存する際に使用する属性名もSecurityFilterChain毎に異なるものにする必要がある。
					HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
					csrfTokenRepository.setSessionAttributeName(
							HttpSessionCsrfTokenRepository.class.getName() + ".CSRF_TOKEN." + name);

					customizer.csrfTokenRepository(csrfTokenRepository);
				})

				.build();
	}

}
