package com.fastcampus.coupon_core.service;

import org.springframework.stereotype.Service;

import com.fastcampus.coupon_core.repository.redis.RedisRepository;
import com.fastcampus.coupon_core.repository.redis.dto.CouponRedisEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceV2 {
    private final RedisRepository redisRepository;
    private final CouponCacheService couponCacheService;

    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponLocalCache(couponId);
        coupon.checkIssuableCoupon();
        issueRequest(couponId, userId, coupon.totalQuantity());
    }

    private void issueRequest(long couponId, long userId, Integer totalIssueQuantity) {
        if (totalIssueQuantity == null) {
            redisRepository.issueRequest(couponId, userId, Integer.MAX_VALUE);
        }
        redisRepository.issueRequest(couponId, userId, totalIssueQuantity);
    }
}
