package com.example.controller;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OneTimePasswordForm;
import com.example.session.LoginUserInfo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oneTimePassword")
public class OneTimePasswordController {

	private final LoginUserInfo loginUserInfo;

	public OneTimePasswordController(LoginUserInfo loginUserInfo) {
		this.loginUserInfo = loginUserInfo;
	}

	@GetMapping
	public String index(OneTimePasswordForm form) {

		// 二要素認証済みならホーム画面へリダイレクトしておく。
		if (loginUserInfo.isPassedTwoFactorAuth()) {
			return "redirect:/home";
		}

		return "oneTimePassword";
	}

	@PostMapping
	public String post(OneTimePasswordForm form, BindingResult bindingResult, HttpServletResponse response) {

		// この例の本質ではないためワンタイムパスワードの計算は行わず、固定値1234を使用している。
		if (!Objects.equals(form.getOneTimePassword(), "1234")) {
			bindingResult.reject("errors.invalid-otp");
			return "oneTimePassword";
		}

		// チェックが入れられていた場合、1週間は2要素認証をスキップできるCookieを発行する。
		if (form.isTrustMe()) {
			// 例なので固定値を設定している。
			Cookie cookie = new Cookie("TRUSTME", "asdfghjk");
			cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(7));
			response.addCookie(cookie);
		}

		loginUserInfo.passTwoFactorAuth();

		return "redirect:/home";
	}
}
