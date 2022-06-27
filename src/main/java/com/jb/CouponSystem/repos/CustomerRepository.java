package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.enums.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);
    Customer findByEmail(String email);

}
