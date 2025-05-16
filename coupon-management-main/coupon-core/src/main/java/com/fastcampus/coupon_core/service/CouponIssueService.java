package com.fastcampus.coupon_core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.coupon_core.exception.CouponIssueException;
import com.fastcampus.coupon_core.model.Coupon;
import com.fastcampus.coupon_core.model.CouponIssue;
import com.fastcampus.coupon_core.repository.mysql.CouponIssueJpaRepository;
import com.fastcampus.coupon_core.repository.mysql.CouponIssueRepository;
import com.fastcampus.coupon_core.repository.mysql.CouponJpaRepository;

import lombok.RequiredArgsConstructor;

import static com.fastcampus.coupon_core.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponIssueService {
    private final CouponIssueRepository couponIssueRepository;
    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;

    @Transactional
    public void issue(long couponId, long userId) {
        Coupon coupon = findCoupon(couponId);
        coupon.issue();
        saveCouponIssue(couponId, userId);
    }

    public Coupon findCoupon(long couponId) {
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssuance(couponId, userId);
        CouponIssue couponIssue = CouponIssue.of(couponId, userId);
        return couponIssueJpaRepository.save(couponIssue);
    }

    private void checkAlreadyIssuance(long couponId, long userId) {
        CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if (issue != null) {
            throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %d, coupon_id: %d".formatted(userId, couponId));
        }
    }
}
