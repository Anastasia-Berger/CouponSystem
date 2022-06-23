package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Coupon;
import com.jb.CouponSystem.beans.Customer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class AdminController extends ClientController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private LoginManager loginManager;


    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            return new ResponseEntity<>(loginManager.login(email, password, ClientType.ADMINISTRATOR), HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(ErrMsg.UNAUTHORIZED_EVENT, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("authorization") String token) {
        tokenManager.deleteToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ////////////////////   COMPANIES EDIT   ////////////////////////

    @PostMapping("add-company")
    public ResponseEntity<?> addCompany(@RequestHeader("authorization") String token, @RequestBody Company company) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.addCompany(company);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("update-company")
    public ResponseEntity<?> updateCompany(@RequestHeader("authorization") String token, @RequestBody Company company) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.updateCompany(company);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("delete-company/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader("authorization") String token, @PathVariable int companyId) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.deleteCompany(companyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("get-companies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader("authorization") String token) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.getAllCompanies();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("get-one-company/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader("authorization") String token, @PathVariable int companyId) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            return new ResponseEntity<Company>(adminService.getOneCompany(companyId), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    ////////////////////   CUSTOMERS EDIT   ////////////////////////

    @PostMapping("add-customer")
    public ResponseEntity<?> addCustomer(@RequestHeader("authorization") String token, @RequestBody Customer customer) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.addCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("update-customer")
    public ResponseEntity<?> updateCustomer(@RequestHeader("authorization") String token, @RequestBody Customer customer) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.updateCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("delete-customer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader("authorization") String token, @PathVariable(name = "id") int id) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            adminService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("get-customers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader("authorization") String token) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("get-one-customer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader("authorization") String token, @PathVariable(name = "id") int id) throws CouponSystemException {
        if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
            return new ResponseEntity<Customer>(adminService.getOneCustomer(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }
}