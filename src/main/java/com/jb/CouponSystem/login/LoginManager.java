//package com.jb.CouponSystem.login;
//
//import com.jb.CouponSystem.enums.ClientType;
//import com.jb.CouponSystem.exeptions.CouponSystemException;
//import com.jb.CouponSystem.exeptions.ErrMsg;
//import com.jb.CouponSystem.security.TokenManager;
//import com.jb.CouponSystem.services.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class LoginManager {
//
//    @Autowired
//    private CustomerServiceImpl customerService;
//    @Autowired
//    private AdminServiceImpl adminService;
//    @Autowired
//    private CompanyServiceImpl companyService;
//    @Autowired
//    private TokenManager tokenManager;
//
//    public LoginResponse login(String email, String password, ClientType clientType) throws CouponSystemException {
//
//        switch (clientType) {
//
//            case ADMINISTRATOR:
//                if (adminService.login(email, password)) {
//                    return new LoginResponse(tokenManager.createToken(adminService));
//                }
//                break;
//
//            case COMPANY:
//                if (companyService.login(email, password)) {
//                    return new LoginResponse(tokenManager.createToken(companyService));
//                }
//                break;
//
//            case CUSTOMER:
//                if (customerService.login(email, password)) {
//                    return new LoginResponse(tokenManager.createToken(customerService));
//                }
//                break;
//
//            default:
//                throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);
//        }
//        return null;
//    }
//}