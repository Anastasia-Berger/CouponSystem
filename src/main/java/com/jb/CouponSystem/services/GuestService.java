package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.repos.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class GuestService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getSingleCoupon(int couponID) {
        return couponRepository.getOne(couponID);
    }
}