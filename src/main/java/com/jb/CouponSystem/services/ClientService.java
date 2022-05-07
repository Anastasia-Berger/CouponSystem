package com.jb.CouponSystem.services;

import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class ClientService {

    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CompanyRepository companyRepository;

    public abstract boolean login(String email, String password) throws CouponSystemException;

}
