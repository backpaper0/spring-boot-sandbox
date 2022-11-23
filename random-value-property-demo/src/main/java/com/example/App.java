package com.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ランダム値を試す。
 *
 * @see org.springframework.boot.env.RandomValuePropertySource
 *
 */
@SpringBootApplication
@ConfigurationProperties(prefix = "my")
public class App implements ApplicationRunner {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private String secret;
	private int number;
	private long bignumber;
	private String uuid;
	private int numberLessThanTen;
	private int numberInRange;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.printf("           secret: %s%n", secret);
		System.out.printf("           number: %s%n", number);
		System.out.printf("        bignumber: %s%n", bignumber);
		System.out.printf("             uuid: %s%n", uuid);
		System.out.printf("numberLessThanTen: %s%n", numberLessThanTen);
		System.out.printf("    numberInRange: %s%n", numberInRange);
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setBignumber(long bignumber) {
		this.bignumber = bignumber;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setNumberLessThanTen(int numberLessThanTen) {
		this.numberLessThanTen = numberLessThanTen;
	}

	public void setNumberInRange(int numberInRange) {
		this.numberInRange = numberInRange;
	}
}
