package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Query(value = "SELECT COUNT(*) FROM customer_vs_coupons WHERE coupons_id=:couponID", nativeQuery = true)
    int allPurchasesOfCoupon(@Param("couponID") int couponID);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customer_vs_coupons WHERE coupons_id=:couponID", nativeQuery = true)
    void deletePurchase(@Param("couponID") int couponID);
}
