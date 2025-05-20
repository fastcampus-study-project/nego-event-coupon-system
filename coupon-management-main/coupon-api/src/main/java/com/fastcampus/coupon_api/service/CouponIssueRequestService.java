package com.fastcampus.coupon_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fastcampus.coupon_api.dto.CouponIssueRequestDto;
import com.fastcampus.coupon_core.service.AsyncCouponIssueServiceV1;
// import com.fastcampus.coupon_core.component.DistributeLockExecutor;
import com.fastcampus.coupon_core.service.CouponIssueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;
    // private final DistributeLockExecutor distributeLockExecutor;

    private final Logger logger = LoggerFactory.getLogger(CouponIssueRequestService.class.getSimpleName());

    public void issueRequestV1(CouponIssueRequestDto request) {
        // distributeLockExecutor.execute("lock_" + request.couponId(), 10000, 10000, () -> {
        couponIssueService.issue(request.couponId(), request.userId());
        // });
        logger.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(request.couponId(), request.userId()));
    }

    public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) {
        asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
    }
}
