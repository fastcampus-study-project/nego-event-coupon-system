package com.fastcampus.coupon_core.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.coupon_core.model.CouponIssue;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {

}
