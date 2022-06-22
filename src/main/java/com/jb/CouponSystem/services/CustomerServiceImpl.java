package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerServiceImpl extends ClientService implements CustomerService {

    private int customerId;
    private Customer currentCustomer;


    @Override
    public boolean login(String email, String password) throws CouponSystemException {

        if (!customerRepository.existsByEmail(email))
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);

        Customer customer = customerRepository.findByEmail(email);

        if (!customer.getPassword().equals(password))
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);


        this.currentCustomer = customer;
        this.customerId = currentCustomer.getId();
        return true;
    }


    public void purchaseCoupon(Coupon coupon) throws CouponSystemException {

        if (coupon.getAmount() <= 0)
            throw new CouponSystemException(ErrMsg.OUT_OF_AMOUNT);

        if (Date.valueOf(LocalDate.now()).after(coupon.getEndDate()))
            throw new CouponSystemException(ErrMsg.EXPIRED);

        for (Coupon customerCoupon : currentCustomer.getCoupons()) {
            if (customerCoupon.getId() == coupon.getId())
                throw new CouponSystemException(ErrMsg.ALREADY_PURCHASED);
        }

        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.saveAndFlush(coupon);

        Customer customerFromDB = customerRepository.getOne(currentCustomer.getId());
        customerFromDB.getCoupons().add(coupon);

        customerRepository.saveAndFlush(customerFromDB);
        currentCustomer.setCoupons(customerFromDB.getCoupons());
    }

    public Boolean isOwnCoupon(int couponId) {
        for (Coupon customerCoupon : currentCustomer.getCoupons()) {
            if (customerCoupon.getId() == couponId)
                return true;
        }
        return false;
    }

    public List<Coupon> getCustomerCoupons() {
        return currentCustomer.getCoupons();
    }


    public List<Coupon> getCustomerCoupons(Category category) {

        List<Coupon> filteredList = currentCustomer.getCoupons().stream()
                .filter(coupon -> category.ordinal() == coupon.getCategory().ordinal())
                .collect(Collectors.toList());
        return filteredList;
    }


    public List<Coupon> getCustomerCoupons(double maxPrice) {

        List<Coupon> filteredList = currentCustomer.getCoupons().stream()
                .filter(coupon -> maxPrice >= coupon.getPrice())
                .collect(Collectors.toList());
        return filteredList;
    }


    public Customer getCustomerDetails() {
        return this.currentCustomer;
    }

}
