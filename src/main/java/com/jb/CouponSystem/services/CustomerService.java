package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

    ////////////////////   LOGIN   ////////////////////////
    public UUID login(String email, String password) throws CouponSystemException;

    ////////////////////   CUSTOMER   ////////////////////////
    public Customer getCustomerDetails();
    public Boolean isOwnCoupon(int couponId);

    ////////////////////   COUPONS   ////////////////////////
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException;

    public List<Coupon> getCustomerCoupons();
    public List<Coupon> getCustomerCoupons(Category category);
    public List<Coupon> getCustomerCoupons(double maxPrice);

}