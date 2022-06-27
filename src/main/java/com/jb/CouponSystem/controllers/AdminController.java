package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.beans.Company;
import com.jb.CouponSystem.beans.Customer;
import com.jb.CouponSystem.dto.LoginReqDto;
import com.jb.CouponSystem.dto.LoginResDto;
import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.AdminService;
import com.jb.CouponSystem.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AdminController  {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenManager tokenManager;

    ////////////////////   LOGIN   ////////////////////////

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@RequestBody LoginReqDto loginReqDto) throws CouponSystemException {
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();
        UUID uuid = adminService.login(email, password);
        ClientType clientType = tokenManager.getClientType(uuid);
        return new LoginResDto(email,uuid,clientType);
    }

    @DeleteMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        int id = tokenManager.getUserID(token);
        tokenManager.deleteEntriesByUserId(id);
    }

    ////////////////////   COMPANIES EDIT   ////////////////////////

    @PostMapping("add-company")
    public ResponseEntity<?> addCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.addCompany(company);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }


    @PutMapping("update-company")
    public ResponseEntity<?> updateCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.updateCompany(company);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("delete-company/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.deleteCompany(companyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("companies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.getAllCompanies();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("company/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            return new ResponseEntity<Company>(adminService.getOneCompany(companyId), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    ////////////////////   CUSTOMERS EDIT   ////////////////////////

    @PostMapping("customer")
    public ResponseEntity<?> addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.addCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("customer")
    public ResponseEntity<?> updateCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.updateCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable(name = "id") int id) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            adminService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("customers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader("Authorization") UUID token, @PathVariable(name = "id") int id) throws CouponSystemException {
        if (tokenManager.isAdmin(token)) {
            return new ResponseEntity<Customer>(adminService.getOneCustomer(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CouponSystemException(ErrMsg.UNAUTHORIZED_EVENT), HttpStatus.UNAUTHORIZED);
    }
}