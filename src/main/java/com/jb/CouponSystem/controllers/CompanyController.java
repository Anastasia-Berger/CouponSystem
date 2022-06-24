package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.login.LoginManager;
import com.jb.CouponSystem.login.TokenManager;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import com.jb.CouponSystem.services.AdminService;
import com.jb.CouponSystem.services.CompanyService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CompanyController extends ClientController{

    @Autowired
    private CompanyService companyService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private LoginManager loginManager;

    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            return new ResponseEntity<>(loginManager.login(email, password, ClientType.COMPANY), HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(ErrMsg.UNAUTHORIZED_EVENT, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("authorization") String token){
        tokenManager.deleteToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ////////////////////   COUPONS   ////////////////////////

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/coupon")
    public CouponDto addCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws CouponSystemException {
        return companyService.addCoupon(coupon);
    }

    @PutMapping("coupon/{id}")
    public ResponseEntity<?> updateCompany(@RequestHeader("Authorization") String token, @PathVariable int id, @RequestBody Coupon coupon) throws CouponSystemException {
        companyService.updateCoupon(coupon);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("coupon/{id}")
    public void deleteCoupon(@RequestHeader("Authorization") String token, @PathVariable int id) {
        companyService.deleteCoupon(id);
    }

    @GetMapping("company-coupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.OK);
    }

    @GetMapping("company/category")
    public ResponseEntity<?> getCoupons(@RequestHeader("Authorization") String token, @RequestParam Category category) {
        return new ResponseEntity<>(companyService.getCompanyCoupons(category), HttpStatus.OK);
    }

    @GetMapping("company/max-price")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") String token, @RequestBody double maxPrice) {
        return new ResponseEntity<>(companyService.getCompanyCoupons(maxPrice), HttpStatus.OK);
    }

    @GetMapping("company/details")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyDetails(@RequestHeader("Authorization") String token) {
        return companyService.getCompanyDetails();
    }

//
//
//
//    @RequestMapping(value = "coupons/images/{uuid}", method = RequestMethod.GET)
//    public String getCouponImage(@PathVariable UUID uuid, HttpServletResponse response) throws Exception {
//        Image image = imageService.getImage(uuid);
//
//        response.setHeader("Content-Disposition", "inline;filename=\"" + image.getId().toString() + "\"");
//        OutputStream out = response.getOutputStream();
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        IOUtils.copy(new ByteArrayInputStream(image.getImage()), out);
//        out.close();
//
//
//        return null;
//    }
//
//
//
//
//
//
//    @PostMapping("register")
//    public ResponseEntity<?> register(@RequestBody RequestRegisterCompany requestRegister) throws CouponSystemException {
//        Company company = Company.builder()
//                .firstName(requestRegister.getFirstName())
//                .email(requestRegister.getEmail())
//                .password(requestRegister.getPassword())
//                .clientType(requestRegister.getClientType())
//                .build();
//        adminService.addCompany(company);
//        String token = loginManager.login(requestRegister.getEmail(), requestRegister.getPassword(),requestRegister.getClientType());
//
//        Information information = tokenManager.getMap().get(token);
//        System.out.println("from client info------->" + information);
//
//        ResponseLogin responseLogin = ResponseLogin.builder()
//                .id(information.getId())
//                .clientType(information.getClientType())
//                .name(information.getName())
//                .token(token)
//                .build();
//        return new ResponseEntity<>(responseLogin,HttpStatus.CREATED);
//    }
//
}