package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsByTitleAndCompanyId(String title, int companyId);

    @Query(value = "SELECT * FROM coupon WHERE title =:title AND company_id=:companyID", nativeQuery = true)
    Coupon getCouponByTitleAndCompanyId(@Param("title") String title, @Param("companyID") int companyID);


    List<Coupon> findAllByCompanyId(int companyID);

    @Query(value = "SELECT * FROM customer_vs_coupons WHERE coupons_id=:couponId", nativeQuery = true)
    List<Integer> allCouponsPurchases(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM customer_vs_coupons", nativeQuery = true)
    Map<Integer, Integer> allPurchases();

    @Query(value = "SELECT COUNT(*) FROM customer_vs_coupons WHERE coupons_id=:couponID", nativeQuery = true)
    int allPurchasesOfCoupon(@Param("couponID") int couponID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customer_vs_coupons WHERE coupons_id=:couponId", nativeQuery = true)
    void deletePurchase(@Param("couponId") int couponId);
}
