package com.fastcampus.coupon_core.exception;

public enum ErrorCode {
    INVALID_COUPON_ISSUE_QUANTITY("발급 가능한 수량을 초과합니다."),
    INVALID_COUPON_ISSUE_DATE("발급 가능한 일자가 아닙니다."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
