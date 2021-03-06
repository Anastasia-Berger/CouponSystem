package com.jb.CouponSystem.clr;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import com.jb.CouponSystem.services.AdminServiceImpl;
import com.jb.CouponSystem.services.CompanyService;
import com.jb.CouponSystem.services.CustomerService;
import com.jb.CouponSystem.shedule.CouponExpirationDailyJob;
import com.jb.CouponSystem.utils.Art;
import com.jb.CouponSystem.utils.TablePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CouponExpirationDailyJob couponExpirationDailyJob;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(Art.BOOTSTRAP);

        Company company1 = Company.builder()
                .name("Company1")

                .email("company1@gmail.com")
                .password("1234")
                .clientType(ClientType.COMPANY)

                .build();

        Company company2 = Company.builder()
                .name("Company2")

                .email("company2@gmail.com")
                .password("1234")
                .clientType(ClientType.COMPANY)

                .build();

        Company company3 = Company.builder()
                .name("Company3")

                .email("company3@gmail.com")
                .password("1234")
                .clientType(ClientType.COMPANY)

                .build();

        Customer customer1 = Customer.builder()
                .firstName("Avi1")
                .lastName("Cohen1")
                .email("avi1@gmail.com")
                .password("1234")
                .clientType(ClientType.CUSTOMER)

                .coupons(Collections.singletonList(couponRepository.getById(1)))
                .build();

        Customer customer2 = Customer.builder()
                .firstName("Avi2")
                .lastName("Cohen2")
                .email("avi2@gmail.com")
                .password("1234")
                .clientType(ClientType.CUSTOMER)

                .build();

        Customer customer3 = Customer.builder()
                .firstName("Avi3")
                .lastName("Cohen3")
                .email("avi3@gmail.com")
                .password("1234")
                .clientType(ClientType.CUSTOMER)

                .build();

        Coupon coupon1 = Coupon.builder()
                .amount(100)
                .price(13.5)
                .image("blabla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .category(Category.FOOD)
                .description("blablabla")
                .title("title 1")
                .company(companyRepository.getById(1))
                .build();

        Coupon coupon2 = Coupon.builder()
                .amount(100)
                .price(14.5)
                .image("blabla2")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .category(Category.ELECTRICITY)
                .description("blablabla2")
                .title("title 2")
                .company(companyRepository.getById(2))
                .build();

        Coupon coupon3 = Coupon.builder()
                .amount(50)
                .price(10)
                .image("Coupon_3")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .category(Category.VACATION)
                .description("blablabla3")
                .title("title 3")
                .company(companyRepository.getById(3))
                .build();

        Coupon coupon4 = Coupon.builder()
                .amount(70)
                .price(10)
                .image("Coupon_4")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .category(Category.VACATION)
                .description("blablabla4")
                .title("title 4")
                .company(company1)
                .build();
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        System.out.println(Art.COMPANY);
        companyRepository.saveAll(Arrays.asList(company1, company2, company3));
        TablePrinter.print(companyRepository.findAll());

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        System.out.println(Art.CUSTOMERS);
        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        TablePrinter.print(customerRepository.findAll());

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        System.out.println(Art.COUPONS);
        couponRepository.saveAll(Arrays.asList(coupon1, coupon2, coupon3));
        TablePrinter.print(couponRepository.findAll());

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        System.out.println(Art.DAYLY_JOB);

    }
}
