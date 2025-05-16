package com.fastcampus.coupon_core.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon_issues")
public class CouponIssue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime dateIssued;

    private LocalDateTime dateUsed;

    @Builder
    private CouponIssue(Long couponId, Long userId, LocalDateTime dateIssued, LocalDateTime dateUsed) {
        this.couponId = couponId;
        this.userId = userId;
        this.dateIssued = dateIssued;
        this.dateUsed = dateUsed;
    }

    public static CouponIssue of(Long couponId, Long userId) {
        return CouponIssue.builder()
            .couponId(couponId)
            .userId(userId)
            .build();
    }
}
