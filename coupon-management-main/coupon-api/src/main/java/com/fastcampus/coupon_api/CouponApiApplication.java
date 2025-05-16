package com.fastcampus.coupon_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.fastcampus.coupon_core.CouponCoreConfiguration;

@Import(CouponCoreConfiguration.class)
@SpringBootApplication
public class CouponApiApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-core,application-api");
		SpringApplication.run(CouponApiApplication.class, args);
	}

}
