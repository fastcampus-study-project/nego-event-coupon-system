package com.fastcampus.coupon_core.repository.redis.dto;

public record CouponIssueRequest(
    long couponId,
    long userId
) {
    
}
