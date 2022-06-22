package com.jb.CouponSystem.shedule;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.repos.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class CouponExpirationDailyJob {

    @Autowired
    private CouponRepository couponRepository;

    @Scheduled(fixedRate = (1000 * 60 * 60 * 24))
    public void deleteExpirationCoupon() {
        List<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            if(coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
                couponRepository.deleteById(coupon.getId());
            }
        }
    }
}