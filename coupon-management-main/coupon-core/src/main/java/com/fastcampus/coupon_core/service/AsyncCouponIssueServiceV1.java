package com.fastcampus.coupon_core.service;

import static com.fastcampus.coupon_core.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.fastcampus.coupon_core.component.DistributeLockExecutor;
import com.fastcampus.coupon_core.exception.CouponIssueException;
import com.fastcampus.coupon_core.model.Coupon;
import com.fastcampus.coupon_core.repository.redis.RedisRepository;
import com.fastcampus.coupon_core.repository.redis.dto.CouponIssueRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import static com.fastcampus.coupon_core.util.CouponRedisUtils.*;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceV1 {
    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final CouponIssueService couponIssueService;
    private final DistributeLockExecutor distributeLockExecutor;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void issue(long couponId, long userId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);
        if(!coupon.availableIssueDate()) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. couponId: %s, issueStart: %s, issueEnd: %s".formatted(couponId, coupon.getDateIssueStart(), coupon.getDateIssueEnd()));
        }
        distributeLockExecutor.execute("lock_%s".formatted(couponId), 3000, 3000, () -> {
            if (!couponIssueRedisService.availableTotalIssueQuantity(coupon.getTotalQuantity(), couponId)) {
                throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY,
                        "발급 가능한 수량을 초과하였습니다. couponId: %s, userId: %s".formatted(couponId, userId));
            }
            if (!couponIssueRedisService.availableUserIssueQuantity(couponId, userId)) {
                throw new CouponIssueException(DUPLICATED_COUPON_ISSUE,
                        "이미 발급된 쿠폰입니다. couponId: %s, userId: %s".formatted(couponId, userId));
            }
            issueRequest(couponId, userId);
        });
    }

    private void issueRequest(long couponId, long userId) {
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(couponId, userId);
        try {
            String value = objectMapper.writeValueAsString(couponIssueRequest);
            redisRepository.sAdd(getIssueRequestKey(couponId), String.valueOf(userId));
            redisRepository.rPush(getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            throw new CouponIssueException(FAIL_COUPON_ISSUE_REQUEST, "input: %s".formatted(couponIssueRequest));
        }
        
    }

}
