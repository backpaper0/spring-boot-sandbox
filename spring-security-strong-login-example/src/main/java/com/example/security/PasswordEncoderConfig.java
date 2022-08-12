package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@Configuration
public class PasswordEncoderConfig {

	@Bean
	public Pbkdf2PasswordEncoder passwordEncoder() {
		// NIST Special Publication 800-63Bを参考にPasswordEncoderを設定する
		// https://pages.nist.gov/800-63-3/sp800-63b.html
		Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder("", 16, 10000, 256);
		passwordEncoder.setAlgorithm(SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		passwordEncoder.setEncodeHashAsBase64(true);
		return passwordEncoder;
	}
}
