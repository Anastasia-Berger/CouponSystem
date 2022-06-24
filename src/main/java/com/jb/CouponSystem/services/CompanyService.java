package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import java.util.List;

public interface CompanyService {

    ////////////////////   LOGIN   ////////////////////////
    public boolean login(String email, String password) throws CouponSystemException;

    ////////////////////   COMPANY DETAILS   ////////////////////////
    public Company getCompanyDetails();
    public int getTotalPurchases();

    ////////////////////   COUPONS   ////////////////////////
    public CouponDto addCoupon(Coupon coupon) throws CouponSystemException;
    public void updateCoupon(Coupon coupon) throws CouponSystemException;
    public void deleteCoupon(int couponId);
    public boolean isTitleExists(String title);

    ////////////////////   LISTS   ////////////////////////
    public List<Coupon> getCompanyCoupons();
    public List<Coupon> getCompanyCoupons(Category category);
    public List<Coupon> getCompanyCoupons(double maxPrice);



}