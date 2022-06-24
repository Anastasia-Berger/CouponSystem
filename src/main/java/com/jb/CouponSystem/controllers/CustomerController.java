package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.login.LoginManager;
import com.jb.CouponSystem.login.TokenManager;
import com.jb.CouponSystem.services.CustomerService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private LoginManager loginManager;


    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable(name = "email") String email, @PathVariable(name = "password") String password) {
        try {
            return new ResponseEntity<>(loginManager.login(email, password, ClientType.CUSTOMER), HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(ErrMsg.UNAUTHORIZED_EVENT, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("authorization") String token) {
        tokenManager.deleteToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ////////////////////   CUSTOMER COUPONS   ////////////////////////

    @GetMapping("customer-coupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("Authorization") String token) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
            CustomerService customerService = (CustomerService) tokenManager.get(token).getClientService();
            return new ResponseEntity<>(customerService.getCustomerCoupons(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("coupons/category")
    public ResponseEntity<?> getCoupons(@RequestHeader("Authorization") String token, @RequestParam Category category) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
            CustomerService customerService = (CustomerService) tokenManager.get(token).getClientService();
            return new ResponseEntity<>(customerService.getCustomerCoupons(category), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("coupons/")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") String token, @RequestBody double maxPrice) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
            CustomerService customerService = (CustomerService) tokenManager.get(token).getClientService();
            return new ResponseEntity<>(customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    ////////////////////   CUSTOMER   ////////////////////////

    @GetMapping("customer/details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader("Authorization") String token) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
            CustomerService customerService = (CustomerService) tokenManager.get(token).getClientService();
            return new ResponseEntity<>(customerService.getCustomerDetails(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("purchase-coupon")
    @SneakyThrows
    public ResponseEntity<?> purchaseCoupon(@RequestHeader("authorization") String token, @RequestBody Coupon coupon) {
        if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
            CustomerService customerService = (CustomerService) tokenManager.get(token).getClientService();
            customerService.purchaseCoupon(coupon);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }


}