package com.fastcampus.coupon_core.repository.mysql;

import org.springframework.stereotype.Repository;

import com.fastcampus.coupon_core.model.CouponIssue;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.fastcampus.coupon_core.model.QCouponIssue.couponIssue;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {

    private final JPQLQueryFactory queryFactory;

    public CouponIssue findFirstCouponIssue(long couponId, long userId) {
        return queryFactory.selectFrom(couponIssue)
            .where(couponIssue.couponId.eq(couponId), couponIssue.userId.eq(userId))
            .fetchFirst();
    }
}
