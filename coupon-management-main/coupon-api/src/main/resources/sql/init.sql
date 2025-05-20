INSERT INTO coupons (
    title, 
    coupon_type, 
    total_quantity, 
    issued_quantity, 
    discount_amount, 
    min_available_amount, 
    date_issue_start, 
    date_issue_end, 
    date_created, 
    date_updated
) VALUES (
    '네고왕 선착순 쿠폰 발급', 
    'FIRST_COME_FIRST_SERVED', 
    500, 
    0, 
    100000, 
    110000, 
    NOW() - INTERVAL 1 DAY, 
    NOW() + INTERVAL 7 DAY, 
    NOW(), 
    NOW()
);
