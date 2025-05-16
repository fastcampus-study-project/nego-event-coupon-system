package com.fastcampus.coupon_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.coupon_api.dto.CouponIssueRequestDto;
import com.fastcampus.coupon_api.dto.CouponIssueResponseDto;
import com.fastcampus.coupon_api.service.CouponIssueRequestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CouponIssueController {
    private final CouponIssueRequestService couponIssueRequestService;

    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issueV1(@RequestBody CouponIssueRequestDto body) {
        couponIssueRequestService.issueRequestV1(body);
        return new CouponIssueResponseDto(true, null);
    }
}
