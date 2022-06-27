package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import java.util.List;
import java.util.UUID;

public interface CompanyService {

    ////////////////////   LOGIN   ////////////////////////
    public UUID login(String email, String password) throws CouponSystemException;

    ////////////////////   COMPANY DETAILS   ////////////////////////
    public Company getCompanyDetails();
    public int getTotalPurchases();

    ////////////////////   COUPONS   ////////////////////////
    public CouponDto addCoupon(int companyID, CouponDto couponDto) throws CouponSystemException;
    public CouponDto updateCoupon(int companyID, int couponID, CouponDto CouponDto) throws CouponSystemException;
    public void deleteCoupon(int couponId);
    public boolean isTitleExists(String title);

    ////////////////////   LISTS   ////////////////////////
    public List<Coupon> getCompanyCoupons();
    public List<Coupon> getCompanyCoupons(Category category);
    public List<Coupon> getCompanyCoupons(double maxPrice);



}