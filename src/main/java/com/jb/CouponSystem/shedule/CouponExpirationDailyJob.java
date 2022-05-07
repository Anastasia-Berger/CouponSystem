package com.jb.CouponSystem.shedule;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableAsync
@RequiredArgsConstructor
public class CouponExpirationDailyJob {

    @Autowired
    private final CouponRepository couponRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Async
    @Scheduled(fixedRate = (1000 * 60 * 60 * 24))
    public void deleteExpiredCoupon() {
        List<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {

            if (Date.valueOf(LocalDate.now()).after(coupon.getEndDate())) {

                couponRepository.deletePurchase(coupon.getId());
                couponRepository.delete(coupon);

                System.out.println(coupon.getTitle() + " was deleted!");
            }
        }
    }
}