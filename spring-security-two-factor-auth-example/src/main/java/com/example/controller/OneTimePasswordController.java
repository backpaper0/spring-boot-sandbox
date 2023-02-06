package com.example.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OneTimePasswordForm;
import com.example.session.LoginUserInfo;

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
	public String post(OneTimePasswordForm form, BindingResult bindingResult) {

		// この例の本質ではないためワンタイムパスワードの計算は行わず、固定値1234を使用する。
		if (!Objects.equals(form.getOneTimePassword(), "1234")) {
			bindingResult.reject("errors.invalid-otp");
			return "oneTimePassword";
		}

		loginUserInfo.passTwoFactorAuth();

		return "redirect:/home";
	}
}
