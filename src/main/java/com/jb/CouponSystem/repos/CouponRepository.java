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

    @Query(value = "SELECT COUNT(*) FROM customer_vs_coupons WHERE coupons_id=:couponID", nativeQuery = true)
    int allPurchasesOfCoupon(@Param("couponID") int couponID);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customer_vs_coupons WHERE coupons_id=:couponID", nativeQuery = true)
    void deletePurchase(@Param("couponID") int couponID);

    /////////////////////////////////////////////////////////////////////////

    @Query(value = "from Coupon where company_id=:companyId")
    List<Coupon> findAllByCompanyId(int companyId);

    @Query(value = "from Coupon where company_id=:companyId AND category=:category")
    List<Coupon> findAllByCompanyIdAndCategory(int companyId, Category category);

    @Query(value = "from Coupon where company_id=:companyId AND price<=:maxPrice")
    List<Coupon> findAllByCompanyIdAndPrice(int companyId, double maxPrice);

    @Query(value = "select couplist.id from Customer c join c.coupons couplist where c.id=:customerId")
    List<Integer> findCouponIdsByCustomerId(int customerId);

    @Transactional
    @Modifying
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Query(value = "delete from Coupon where end_date<=:date")
    int deleteAllByEndDateBefore(LocalDate date) throws CouponSystemException;

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from customer_coupon where customer_id=:customerId and coupon_id=:couponId")
    void deleteCouponPurchase(int customerId, int couponId) throws CouponSystemException;

    @Query(value = "select couplist.id from Customer c join c.coupons couplist where c.id=:customerId and couplist.id=:couponId")
    List<Integer> wasPurchased(int customerId, int couponId) throws CouponSystemException;


    @Query(value = "select amount from Coupon where id=:couponId")
    int getAmount(int couponId) throws CouponSystemException;


	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from customer_coupon where coupon_id=:couponId")
	void deleteCouponHistory(int couponId) throws CouponSystemException;
}
