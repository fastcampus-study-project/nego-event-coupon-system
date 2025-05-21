package com.fastcampus.coupon_consumer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.coupon_core.CouponCoreConfiguration;

@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.config.name=application-core")
@SpringBootTest(classes = CouponCoreConfiguration.class)
public class TestConfig {

}
