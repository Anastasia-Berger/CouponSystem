package com.jb.CouponSystem.login;

import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.services.AdminService;
import com.jb.CouponSystem.services.ClientService;
import com.jb.CouponSystem.services.CompanyService;
import com.jb.CouponSystem.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoginManager {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    public ClientService login(ClientType clientType, String email, String password) throws CouponSystemException {
        ClientService result = null;
        switch (clientType) {
            case ADMINISTRATOR:
                result = (adminService.login(email, password))? adminService:null;
                break;
            case COMPANY:
                result = (companyService.login(email, password))? companyService:null;
                break;
            case CUSTOMER:
                result = (customerService.login(email, password))? customerService:null;
                break;
            default:
                throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);
        }
        if (result == null) {
            throw new CouponSystemException(ErrMsg.INCORRECT_LOGIN);
        }
        return result;
    }
}