package com.fastcampus.coupon_api.dto;

public record CouponIssueRequestDto(
    long userId,
    long couponId
) {
    
}
