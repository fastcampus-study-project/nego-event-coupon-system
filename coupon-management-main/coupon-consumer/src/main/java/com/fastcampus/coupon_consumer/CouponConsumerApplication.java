package com.fastcampus.coupon_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.fastcampus.coupon_core.CouponCoreConfiguration;

@Import(CouponCoreConfiguration.class)
@SpringBootApplication
public class CouponConsumerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-core, application-consumer");
		SpringApplication.run(CouponConsumerApplication.class, args);
	}

}
