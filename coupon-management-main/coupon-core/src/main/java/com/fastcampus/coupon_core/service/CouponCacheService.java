package com.fastcampus.coupon_core.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fastcampus.coupon_core.model.Coupon;
import com.fastcampus.coupon_core.repository.redis.dto.CouponRedisEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponCacheService {
    private final CouponIssueService couponIssueService;

    @Cacheable(cacheNames = "coupon")
    public CouponRedisEntity getCouponCache(Long couponId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);
        return new CouponRedisEntity(coupon);
    }
}
