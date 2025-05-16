package com.fastcampus.coupon_api.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fastcampus.coupon_api.dto.CouponIssueResponseDto;
import com.fastcampus.coupon_core.exception.CouponIssueException;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler(CouponIssueException.class)
    public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException exception) {
        return new CouponIssueResponseDto(false, exception.getErrorCode().message);
    }
}

