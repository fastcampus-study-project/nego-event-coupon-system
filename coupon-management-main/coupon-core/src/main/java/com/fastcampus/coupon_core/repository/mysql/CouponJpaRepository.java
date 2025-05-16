package com.fastcampus.coupon_core.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.coupon_core.model.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

}
