package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.exeptions.CouponSystemException;

import java.util.List;

public interface AdminService {

    ////////////////////   LOGIN   ////////////////////////
    public boolean login(String email, String password) throws CouponSystemException;

    ////////////////////   COMPANIES   ////////////////////////
    public void addCompany(Company company) throws CouponSystemException;
    public void updateCompany(Company company) throws CouponSystemException;
    public void deleteCompany(int companyID) throws CouponSystemException;
    public List<Company> getAllCompanies();
    public Company getOneCompany(int companyID);
    public Boolean companyExistsByName(String companyName);
    public boolean companyExistsByEmail(String email);

    // Customers
    public void addCustomer(Customer customer) throws CouponSystemException;
    public void updateCustomer(Customer customer);
    public void deleteCustomer(int customerId);
    public Customer getOneCustomer(int customerId);
    public List<Customer> getAllCustomers();
    public boolean customerExistsByEmail(String email);

    // Coupons
    public List<Coupon> getAllCoupons();

}
