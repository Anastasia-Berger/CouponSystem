package com.jb.CouponSystem.login;

import com.jb.CouponSystem.enums.ClientType;
import com.jb.CouponSystem.exeptions.CouponSystemException;
import com.jb.CouponSystem.exeptions.ErrMsg;
import com.jb.CouponSystem.services.AdminService;
import com.jb.CouponSystem.services.ClientService;

import com.jb.CouponSystem.services.CompanyService;
import com.jb.CouponSystem.services.CustomerService;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenManager {

    private Map<String, UserData> tokens;

    public String createToken(ClientService clientService) {
        UserData userData = new UserData(clientService, System.currentTimeMillis());
        String token = UUID.randomUUID().toString();
        tokens.put(token, userData);
        return token;
    }

    public boolean isTokenExist(String token) throws CouponSystemException {
        if (tokens.containsKey(token)) {
            return true;
        }
        throw new CouponSystemException(ErrMsg.TOKEN_NOT_EXIST);
    }

    public boolean isTokenClientType(String token, ClientType clientType) throws CouponSystemException {
        if (isTokenExist(token)) {
            UserData userData = tokens.get(token);
            switch (clientType) {
                case ADMINISTRATOR:
                    return userData.getClientService() instanceof AdminService;
                case COMPANY:
                    return userData.getClientService() instanceof CompanyService;
                case CUSTOMER:
                    return userData.getClientService() instanceof CustomerService;
            }
        }
        return false;
    }

    public UserData get(String token) {
        return tokens.get(token);
    }

    public UserData deleteToken(String token) {
        return tokens.remove(token);
    }

    public void deleteExpiredToken() {
        tokens.values().removeIf((UserData userData) -> userData.getTimeStamp() + 30 * 60 * 1000 <= System.currentTimeMillis());
    }
}