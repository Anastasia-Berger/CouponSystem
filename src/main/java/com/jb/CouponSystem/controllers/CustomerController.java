package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.LoginReqDto;
import com.jb.CouponSystem.dto.LoginResDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CustomerService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private TokenManager tokenManager;


    private LoginResDto loginResDto;
    private LoginReqDto loginReqDto;

    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@RequestBody LoginReqDto loginReqDto) throws CouponSystemException {
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();
        UUID uuid = customerService.login(email, password);
        ClientType clientType = tokenManager.getClientType(uuid);
        return new LoginResDto(email,uuid,clientType);
    }

    @DeleteMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        int id = tokenManager.getUserID(token);
        tokenManager.deleteEntriesByUserId(id);
    }

    ////////////////////   CUSTOMER COUPONS   ////////////////////////

    @GetMapping("coupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        if (tokenManager.getClientType(token).equals(ClientType.CUSTOMER)) {
            return new ResponseEntity<>(customerService.getCustomerCoupons(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("coupons/category")
    public ResponseEntity<?> getCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSystemException {
        if (tokenManager.getClientType(token).equals(ClientType.CUSTOMER)) {
            return new ResponseEntity<>(customerService.getCustomerCoupons(category), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("coupons/max-price")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestBody double maxPrice) throws CouponSystemException {
        if (tokenManager.getClientType(token).equals(ClientType.CUSTOMER)) {
            return new ResponseEntity<>(customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    ////////////////////   CUSTOMER   ////////////////////////

    @GetMapping("customer/details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        if (tokenManager.getClientType(token).equals(ClientType.CUSTOMER)) {
            return new ResponseEntity<>(customerService.getCustomerDetails(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("purchase-coupon")
    @SneakyThrows
    public ResponseEntity<?> purchaseCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) {
        if (tokenManager.getClientType(token).equals(ClientType.CUSTOMER)) {
            customerService.purchaseCoupon(coupon);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }


}