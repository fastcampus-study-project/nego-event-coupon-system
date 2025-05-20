package com.fastcampus.coupon_core.service;

import org.springframework.stereotype.Service;

import com.fastcampus.coupon_core.repository.redis.RedisRepository;

import lombok.RequiredArgsConstructor;

import static com.fastcampus.coupon_core.util.CouponRedisUtils.*;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {
    private final RedisRepository redisRepository;

    public boolean availableTotalIssueQuantity(Integer totalQuantity, long couponId) {
        if (totalQuantity == null) {
            return true;
        }
        String key = getIssueRequestKey(couponId);
        return totalQuantity > redisRepository.sCard(key);
    }

    public boolean availableUserIssueQuantity(long couponId, long userId) {
        String key = getIssueRequestKey(couponId);
        return !redisRepository.sIsMember(key, String.valueOf(userId));
    }
}
