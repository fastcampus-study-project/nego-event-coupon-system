package com.fastcampus.coupon_core.service;

import org.springframework.stereotype.Service;

import com.fastcampus.coupon_core.repository.redis.RedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {
    private final RedisRepository redisRepository;

    
}
