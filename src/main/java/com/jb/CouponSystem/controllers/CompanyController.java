package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.dto.LoginReqDto;
import com.jb.CouponSystem.dto.LoginResDto;
import com.jb.CouponSystem.enums.Category;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private TokenManager tokenManager;

    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@RequestBody LoginReqDto loginReqDto) throws CouponSystemException {
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();
        UUID uuid = companyService.login(email, password);
        ClientType clientType = tokenManager.getClientType(uuid);
        return new LoginResDto(email, uuid, clientType);
    }

    @DeleteMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        int id = tokenManager.getUserID(token);
        tokenManager.deleteEntriesByUserId(id);
    }

    ////////////////////   COUPONS   ////////////////////////

    @PostMapping("coupon/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CouponDto addCoupon(@RequestHeader("Authorization") UUID token, @RequestBody CouponDto couponDto) throws CouponSystemException {
        int userId = tokenManager.getUserID(token);
        return companyService.addCoupon(userId, couponDto);
    }

    @PutMapping("coupon/{id}")
    public ResponseEntity<?> updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody CouponDto couponDto) throws CouponSystemException {
        companyService.updateCoupon(id, couponDto.getId(), couponDto);
        return new ResponseEntity<>(couponDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("coupon/{id}")
    public void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int id) {
        companyService.deleteCoupon(id);
    }

    @GetMapping("coupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") UUID token) {
        return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.OK);
    }

    @GetMapping("company/category")
    public ResponseEntity<?> getCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) {
        return new ResponseEntity<>(companyService.getCompanyCoupons(category), HttpStatus.OK);
    }

    @GetMapping("company/max-price")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestBody double maxPrice) {
        return new ResponseEntity<>(companyService.getCompanyCoupons(maxPrice), HttpStatus.OK);
    }

    @GetMapping("company/details")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyDetails(@RequestHeader("Authorization") UUID token) {
        return companyService.getCompanyDetails();
    }

//
//    @RequestMapping(value = "coupons/images/{uuid}", method = RequestMethod.GET)
//    public String getCouponImage(@PathVariable UUID token, HttpServletResponse response) throws Exception {
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