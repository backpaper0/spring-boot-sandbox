package com.example;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(properties = "spring.session.store-type=none")
@AutoConfigureMockMvc
public class LoginTest {

	@Autowired
	MockMvc mvc;

	@Test
	void showLoginPage() throws Exception {
		mvc.perform(get("/login"))
				.andExpectAll(
						status().isOk(),
						view().name("login"));
	}

	@Test
	void redirectToLoginPage() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/"))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrlPattern("**/login"))
				.andReturn();

		mvc.perform(get(mvcResult.getResponse().getHeader("Location")))
				.andExpectAll(
						status().isOk(),
						view().name("login"));
	}

	/**
	 * 正常にログイン。
	 * 
	 * @throws Exception
	 */
	@Test
	void login() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user01")
				.param("password", "pass")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/"),
						authenticated());
	}

	/**
	 * パスワードの誤り。
	 * 
	 * @throws Exception
	 */
	@Test
	void mistakePassword() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user02")
				.param("password", "mistake")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/login?error"),
						unauthenticated(),
						request().sessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
								isA(BadCredentialsException.class)));
	}

	/**
	 * 存在しないアカウント。
	 * 
	 * @throws Exception
	 */
	@Test
	void userNotExists() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user99")
				.param("password", "pass")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/login?error"),
						unauthenticated(),
						request().sessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
								isA(BadCredentialsException.class)));
	}

	/**
	 * アカウントロック。
	 * 
	 * @throws Exception
	 */
	@Test
	void lockedUser() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user20")
				.param("password", "pass")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/login?error"),
						unauthenticated(),
						request().sessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
								isA(LockedException.class)));
	}

	/**
	 * パスワードの有効期限切れ。
	 * 
	 * @throws Exception
	 */
	@Test
	void passwordExpired() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user30")
				.param("password", "pass")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/login?error"),
						unauthenticated(),
						request().sessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
								isA(CredentialsExpiredException.class)));
	}

	/**
	 * 有効期間よりも前
	 * 
	 * @throws Exception
	 */
	@Test
	void beforeValidityFrom() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user40")
				.param("password", "pass")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/login?error"),
						unauthenticated(),
						request().sessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
								isA(AccountExpiredException.class)));
	}

	/**
	 * 有効期間よりも後。
	 * 
	 * @throws Exception
	 */
	@Test
	void afterValidityTo() throws Exception {
		mvc.perform(post("/login")
				.param("username", "user50")
				.param("password", "pass")
				.with(csrf()))
				.andExpectAll(
						status().is3xxRedirection(),
						redirectedUrl("/login?error"),
						unauthenticated(),
						request().sessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
								isA(AccountExpiredException.class)));
	}
}
