package com.paymybuddy.webpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.paymybuddy.webpay.dao","com.paymybuddy.webpay.model",
		"com.paymybuddy.webpay.service","com.paymybuddy.webpay.config"})
public class WebpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebpayApplication.class, args);
	}

}
