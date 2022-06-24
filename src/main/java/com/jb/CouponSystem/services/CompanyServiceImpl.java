package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.mappers.CouponMapper;
import com.jb.CouponSystem.repos.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyServiceImpl extends ClientService implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    private CouponMapper couponMapper;

    private int companyID;
    private Company currentCompany;

    @Override
    public boolean login(String email, String password) throws CouponSystemException {

        if (!companyRepository.existsByEmail(email)) {
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);
        }

        Company company = companyRepository.findByEmail(email);

        if (!company.getPassword().equals(password))
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);

        this.currentCompany = company;
        this.companyID = currentCompany.getId();

        return true;
    }

    public CouponDto addCoupon(Coupon coupon) throws CouponSystemException {

        for (Coupon companyCoupon : currentCompany.getCoupons()) {
            if (companyCoupon.getTitle().equals(coupon.getTitle()))
                throw new CouponSystemException(ErrMsg.ID_ALREADY_EXIST);
        }

        //Saving coupon as ManyToOne bi-directional will automatically update the company coupon list
//        couponRepository.save(coupon);

        // Updating the plain object to include all new coupons.
        List<Coupon> updatedCouponList = companyRepository.getOne(currentCompany.getId()).getCoupons();
        currentCompany.setCoupons(updatedCouponList);
        return couponMapper.toDto(couponRepository.save(coupon));
    }

    public void updateCoupon(Coupon coupon) throws CouponSystemException {

        // ** Checking if coupon id is changed in the Coupon id setter **

        // Comparing companies id's between coupon in DB and the new coupon
        int CompanyOfCoupon = couponRepository.getOne(coupon.getId()).getCompany().getId();
        if (CompanyOfCoupon != coupon.getCompany().getId())
            throw new CouponSystemException(ErrMsg.ILLEGAL_ACTION_EXCEPTION);

        if (coupon.getAmount() <= 0) throw new CouponSystemException(ErrMsg.ZERO_VALUE);
        if (coupon.getPrice() <= 0) throw new CouponSystemException(ErrMsg.ZERO_VALUE);

        couponRepository.saveAndFlush(coupon);

        List<Coupon> updatedCouponList = companyRepository.getOne(currentCompany.getId()).getCoupons();
        currentCompany.setCoupons(updatedCouponList);
    }


    public void deleteCoupon(int couponId) {
        couponRepository.deleteById(couponId);
        couponRepository.deletePurchase(couponId);
        currentCompany.getCoupons().removeIf(coupon -> coupon.getId() == couponId);
    }

    public List<Coupon> getCompanyCoupons() {
        return currentCompany.getCoupons();
    }

    public List<Coupon> getCompanyCoupons(Category category) {

        List<Coupon> listByCategory = currentCompany.getCoupons().stream()
                .filter(coupon -> category.ordinal() == coupon.getCategory().ordinal())
                .collect(Collectors.toList());

        return listByCategory;
    }


    public List<Coupon> getCompanyCoupons(double maxPrice) {

        List<Coupon> listByPrice = currentCompany.getCoupons().stream()
                .filter(coupon -> maxPrice >= coupon.getPrice())
                .collect(Collectors.toList());

        return listByPrice;
    }

    public boolean isTitleExists(String title) {
        long isExists = this.currentCompany.getCoupons().stream()
                .filter(coupon -> coupon.getTitle().toLowerCase().equals(title.toLowerCase())).count();

        return isExists > 0;

    }

    public int getTotalPurchases() {
        int sumOfPurchases = 0;
        for (Coupon coupon : currentCompany.getCoupons()) {
            System.out.println(coupon);
            System.out.println(couponRepository.allPurchasesOfCoupon(coupon.getId()));
            sumOfPurchases += couponRepository.allPurchasesOfCoupon(coupon.getId());
        }

        return sumOfPurchases;
    }

    public Company getCompanyDetails() {
        return currentCompany;
    }


}
