package com.fastcampus.coupon_core.service;

import java.util.Collection;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.fastcampus.coupon_core.TestConfig;

import static com.fastcampus.coupon_core.util.CouponRedisUtils.*;

public class CouponIssueRedisServiceTest extends TestConfig {

    @Autowired
    private CouponIssueRedisService couponIssueRedisService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void clear() {
        Collection<String> redisKeys = redisTemplate.keys("*");
        redisTemplate.delete(redisKeys);
    }

    @Test
    @DisplayName("쿠폰 수량 검증 - 발급 가능 수량이 존재하면 true를 반환한다")
    void availableTotalIssueQuantity_1() {
        // given
        int totalIssueQuantity = 10;
        long couponId = 1;
        // when
        boolean result = couponIssueRedisService.availableTotalIssueQuantity(totalIssueQuantity, couponId);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("쿠폰 수량 검증 - 발급 가능 수량이 모두 소진되면 false를 반환한다")
    void availableTotalIssueQuantity_2() {
        // given
        int totalIssueQuantity = 10;
        long couponId = 1;
        IntStream.range(0, totalIssueQuantity).forEach(userId -> {
            redisTemplate.opsForSet().add(getIssueRequestKey(couponId), String.valueOf(userId));
        });
        // when
        boolean result = couponIssueRedisService.availableTotalIssueQuantity(totalIssueQuantity, couponId);
        // then
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하지 않으면 true를 반환한다.")
    void availableUserIssueQuantity_1() {
        // given
        long couponId = 1;
        long userId = 1;
        // when
        boolean result = couponIssueRedisService.availableUserIssueQuantity(couponId, userId);
        // then
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하면 false를 반환한다.")
    void availableUserIssueQuantity_2() {
        // given
        long couponId = 1;
        long userId = 1;
        redisTemplate.opsForSet().add(getIssueRequestKey(couponId), String.valueOf(userId));
        // when
        boolean result = couponIssueRedisService.availableUserIssueQuantity(couponId, userId);
        // then
        Assertions.assertFalse(result);
    }
    
}
