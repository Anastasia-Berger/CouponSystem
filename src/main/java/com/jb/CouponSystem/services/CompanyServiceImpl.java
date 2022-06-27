package com.jb.CouponSystem.services;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.mappers.CouponMapper;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.security.Information;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl extends ClientService  implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    private CouponMapper couponMapper;

    @Autowired
    private TokenManager tokenManager;

    private int companyID;
    private Company currentCompany;

    @Override
    public UUID login(String email, String password) throws CouponSystemException {
        if (!companyRepository.existsByEmail(email))
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);

        Company company = companyRepository.findByEmail(email);

        if (!company.getPassword().equals(password))
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);

        int companyId = company.getId();
        ClientType clientType = company.getClientType();

        Information information = new Information(companyId, email, clientType);
        return tokenManager.addToken(information);
    }

    @Override
    public CouponDto addCoupon(int companyID, CouponDto couponDto) {
        Coupon coupon = couponMapper.toDao(couponDto);
        Company company = companyRepository.getById(companyID);
        coupon.setCompany(company);
        return couponMapper.toDto(couponRepository.save(coupon));
    }

    public CouponDto updateCoupon(int companyID, int couponID, CouponDto couponDto) throws CouponSystemException {

        // Comparing companies id's between coupon in DB and the new coupon
        int CompanyOfCoupon = couponRepository.getById(couponDto.getId()).getCompany().getId();
        if (CompanyOfCoupon != couponDto.getCompany().getId())
            throw new CouponSystemException(ErrMsg.ILLEGAL_ACTION_EXCEPTION);

        if (couponDto.getAmount() <= 0) throw new CouponSystemException(ErrMsg.ZERO_VALUE);
        if (couponDto.getPrice() <= 0) throw new CouponSystemException(ErrMsg.ZERO_VALUE);

        couponDto.setId(couponID);
        Coupon coupon = couponMapper.toDao(couponDto);

        Company company = companyRepository.getById(companyID);

        coupon.setCompany(company);
        return couponMapper.toDto(couponRepository.saveAndFlush(coupon));
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
