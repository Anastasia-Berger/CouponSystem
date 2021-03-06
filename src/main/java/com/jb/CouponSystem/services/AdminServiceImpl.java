package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import com.jb.CouponSystem.security.Information;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {

    private final String ADMIN_EMAIL = "admin@admin.com";
    private final String ADMIN_PASSWORD = "admin";

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public UUID login(String email, String password) throws CouponSystemException {
        if (!email.equals(ADMIN_EMAIL)) throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);
        if (!password.equals(ADMIN_PASSWORD)) throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);

        ClientType clientType = ClientType.ADMINISTRATOR;
        Information information = new Information(email, clientType);
        return tokenManager.addToken(information);
    }

    public void addCompany(Company company) throws CouponSystemException {
        // Check name
        if (companyRepository.existsByName(company.getName())) {
            throw new CouponSystemException(ErrMsg.COMPANY_ALREADY_EXIST);
        }
        // Check e-mail
        if (companyRepository.existsByEmail((company.getEmail()))) {
            throw new CouponSystemException(ErrMsg.EMAIL_ALREADY_EXIST);
        }
        companyRepository.save(company);
    }

    public void updateCompany(Company company) throws CouponSystemException {

        if (!companyRepository.existsById(company.getId()))
            throw new CouponSystemException(ErrMsg.ID_NOT_EXIST);

        if (!companyRepository.getOne(company.getId()).getName().equals(company.getName())) {
            throw new CouponSystemException(ErrMsg.ILLEGAL_ACTION_EXCEPTION);
        }

        companyRepository.saveAndFlush(company);

    }

    public void deleteCompany(int companyID) throws CouponSystemException {

        if (!companyRepository.existsById(companyID)) throw new CouponSystemException(ErrMsg.ID_NOT_EXIST);
        Company thisCompany = companyRepository.getOne(companyID);


        if (thisCompany.getCoupons().size() > 0) {
            for (Coupon coupon : thisCompany.getCoupons()) {
                couponRepository.deletePurchase(coupon.getId());
            }
        }

        companyRepository.delete(thisCompany);
    }


    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }


    public Company getOneCompany(int companyId) {
        return companyRepository.getOne(companyId);
    }


    public void addCustomer(Customer customer) throws CouponSystemException {
        if (customerRepository.existsByEmail(customer.getEmail()))
            throw new CouponSystemException(ErrMsg.EMAIL_ALREADY_EXIST);
        customerRepository.save(customer);
    }


    public void updateCustomer(Customer customer) {
        // Checking if ID is changed in the Customer setId()
        customerRepository.saveAndFlush(customer);
    }


    public void deleteCustomer(int customerId) {
        // ManyToMany bi-directional connection will delete all customer purchases
        customerRepository.delete(customerRepository.getOne(customerId));

    }

    public boolean companyExistsByName(String companyName) {
        return this.companyRepository.existsByName(companyName);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean companyExistsByEmail(String email) {
        return this.companyRepository.existsByEmail(email);
    }

    public boolean customerExistsByEmail(String email) {
        return this.customerRepository.existsByEmail(email);
    }


    public Customer getOneCustomer(int customerId) {
        return customerRepository.getOne(customerId);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}
