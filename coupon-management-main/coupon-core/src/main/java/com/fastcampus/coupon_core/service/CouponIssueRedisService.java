package com.fastcampus.coupon_core.service;

import org.springframework.stereotype.Service;

import com.fastcampus.coupon_core.exception.CouponIssueException;
import com.fastcampus.coupon_core.repository.redis.RedisRepository;
import com.fastcampus.coupon_core.repository.redis.dto.CouponRedisEntity;

import lombok.RequiredArgsConstructor;

import static com.fastcampus.coupon_core.exception.ErrorCode.*;
import static com.fastcampus.coupon_core.util.CouponRedisUtils.*;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {
    private final RedisRepository redisRepository;

    public void checkCouponIssueQuantity(CouponRedisEntity coupon, long userId) {
        if (!availableUserIssueQuantity(coupon.id(), userId)) {
            throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "발급 가능한 수량을 초과합니다. couponId : %s, userId: %s".formatted(coupon.id(), userId));
        }
        if (!availableTotalIssueQuantity(coupon.totalQuantity(), coupon.id())) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY, "발급 가능한 수량을 초과합니다. couponId : %s, userId : %s".formatted(coupon.id(), userId));
        }
    }

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
