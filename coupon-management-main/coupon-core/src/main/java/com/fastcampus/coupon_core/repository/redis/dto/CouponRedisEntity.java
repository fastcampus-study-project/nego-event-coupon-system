package com.fastcampus.coupon_core.repository.redis.dto;

import static com.fastcampus.coupon_core.exception.ErrorCode.*;

import java.time.LocalDateTime;

import com.fastcampus.coupon_core.exception.CouponIssueException;
import com.fastcampus.coupon_core.model.Coupon;
import com.fastcampus.coupon_core.model.CouponType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public record CouponRedisEntity(
    Long id,
    
    CouponType couponType,
    
    Integer totalQuantity,

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateIssueStart,
    
    @JsonSerialize(using = LocalDateTimeSerializer.class) 
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime dateIssueEnd
) {
    public CouponRedisEntity(Coupon coupon) {
        this (
            coupon.getId(),
            coupon.getCouponType(),
            coupon.getTotalQuantity(),
            coupon.getDateIssueStart(),
            coupon.getDateIssueEnd()
        );
    }

    private boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueEnd.isAfter(now) && dateIssueStart.isBefore(now);
    }

    public void checkIssuableCoupon() {
        if (!availableIssueDate()) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. couponId: %s, issueStart: %s, issueEnd: %s".formatted(id, dateIssueStart, dateIssueEnd));
        }
    }
    
}
