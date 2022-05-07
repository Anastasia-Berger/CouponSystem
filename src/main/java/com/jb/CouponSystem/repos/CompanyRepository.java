package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {


    boolean existsByEmail(String email);

    Company findByEmail(String email);
    boolean existsByName(String name);

}
